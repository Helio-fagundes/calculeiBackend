package application.calculei.usecase.tj_11960;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.Indice_TJ_L11960_Selic;
import application.calculei.infraestructure.entity.SelicMensal;
import application.calculei.infraestructure.repository.indice_tj_L11960_selic.TjL11960SelicIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;
import application.calculei.usecase.tj_11960.dto.CalculateTj11960BetweenDateRequest;
import application.calculei.usecase.tj_11960.dto.CalculateTj11960BetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateTj11960SelicValueBetweenDates {

    private static final LocalDate CUT_OFF_DATE = LocalDate.of(2021, 12, 1);
    private static final int SCALE = 10;

    private final TjL11960SelicIndexRepository tjIndexRepository;
    private final SelicMensalIndexRepository selicIndexRepository;
    private final DateUtils dateUtils;

    public CalculateTj11960SelicValueBetweenDates(
            TjL11960SelicIndexRepository tjIndexRepository,
            SelicMensalIndexRepository selicIndexRepository
    ) {
        this.tjIndexRepository = tjIndexRepository;
        this.selicIndexRepository = selicIndexRepository;
        this.dateUtils = new DateUtils();
    }

    public CalculateTj11960BetweenDateResponse execute(
            CalculateTj11960BetweenDateRequest request
    ) {
        validateDates(request.dataInit(), request.dataFinal());

        long businessDays = dateUtils.businessDays(
                request.dataInit(),
                request.dataFinal()
        );

        BigDecimal accumulatedFactor = calculateAccumulatedFactor(
                request.dataInit(),
                request.dataFinal()
        );

        BigDecimal finalValue = calculateFinalValue(
                request.valor(),
                accumulatedFactor
        );

        return new CalculateTj11960BetweenDateResponse(
                request.dataInit(),
                request.dataFinal(),
                businessDays,
                finalValue,
                accumulatedFactor
        );
    }

    private BigDecimal calculateAccumulatedFactor(LocalDate startDate, LocalDate endDate) {

        LocalDate normalizedStart = startDate.withDayOfMonth(1);
        LocalDate normalizedEnd = endDate.withDayOfMonth(1);

        if (normalizedEnd.isBefore(CUT_OFF_DATE)) {
            return calculateTjFactor(normalizedStart, normalizedEnd);
        }

        if (!normalizedStart.isBefore(CUT_OFF_DATE)) {
            return calculateSelicFactor(normalizedStart, normalizedEnd);
        }

        BigDecimal tjFactor = calculateTjFactor(
                normalizedStart,
                CUT_OFF_DATE.minusMonths(1)
        );

        BigDecimal selicFactor = calculateSelicFactor(
                CUT_OFF_DATE,
                normalizedEnd
        );

        return tjFactor.multiply(selicFactor)
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTjFactor(LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);

        Indice_TJ_L11960_Selic startIndex = tjIndexRepository.findByDataInit(startDate);
        validateIndex(startIndex, startDate);

        return startIndex.getFator().setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateSelicFactor(LocalDate startDate, LocalDate endDate) {

        validateDates(startDate, endDate);

        List<SelicMensal> indexes = selicIndexRepository
                .findByDataInitBetween(startDate, endDate);

        if (indexes.isEmpty()) {
            throw new RuntimeException(
                    "Nenhum índice SELIC encontrado entre: " + startDate + " e " + endDate
            );
        }

        BigDecimal accumulatedRate = indexes.stream()
                .map(SelicMensal::getFator)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BigDecimal.ONE.add(
                accumulatedRate.divide(BigDecimal.valueOf(100), SCALE, RoundingMode.HALF_UP)
        );
    }

    private BigDecimal calculateFinalValue(Double initialValue, BigDecimal factor) {
        return BigDecimal.valueOf(initialValue)
                .multiply(factor)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "Data final deve ser maior ou igual à data inicial."
            );
        }
    }

    private void validateIndex(Indice_TJ_L11960_Selic index, LocalDate date) {
        if (index == null) {
            throw new RuntimeException("Índice não encontrado para a data: " + date);
        }
    }
}
