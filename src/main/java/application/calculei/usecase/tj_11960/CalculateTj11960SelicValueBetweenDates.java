package application.calculei.usecase.tj_11960;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.tj_11960.dto.CalculateTj11960BetweenDateRequest;
import application.calculei.usecase.tj_11960.dto.CalculateTj11960BetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateTj11960SelicValueBetweenDates {

    private static final LocalDate CUT_OFF_DATE = LocalDate.of(2021, 12, 1);
    private static final int SCALE = 10;

    private final IndexRepository tjRepository;
    private final IndexRepository selicRepository;

    public CalculateTj11960SelicValueBetweenDates(IndexRepository tjRepository, IndexRepository selicRepository) {
        this.tjRepository = tjRepository;
        this.selicRepository = selicRepository;
    }

    public CalculateTj11960BetweenDateResponse execute(CalculateTj11960BetweenDateRequest request) {
        validate(request.startDate(), request.endDate());

        BigDecimal accumulatedFactor = calculateHybridFactor(request.startDate(), request.endDate());

        BigDecimal finalValue = applyFactor(request.amount(), accumulatedFactor);
        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateTj11960BetweenDateResponse(
                request.startDate(), request.endDate(), businessDays, finalValue, accumulatedFactor
        );
    }

    private BigDecimal calculateHybridFactor(LocalDate startDate, LocalDate endDate) {
        LocalDate normStart = startDate.withDayOfMonth(1);
        LocalDate normEnd = endDate.withDayOfMonth(1);

        if (normEnd.isBefore(CUT_OFF_DATE)) {
            return fetchTjFactor(normStart);
        }

        if (!normStart.isBefore(CUT_OFF_DATE)) {
            return calculateSelicFactor(normStart, normEnd);
        }

        BigDecimal tjPart = fetchTjFactor(normStart);
        BigDecimal selicPart = calculateSelicFactor(CUT_OFF_DATE, normEnd);

        return tjPart.multiply(selicPart).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal fetchTjFactor(LocalDate date) {
        Index index = tjRepository.findDataInit(date);
        if (index == null) throw new DataNotFoundException("Índice TJ não encontrado para: " + date);
        return index.getFator();
    }

    private BigDecimal calculateSelicFactor(LocalDate startDate, LocalDate endDate) {
        List<Index> selicIndexes = selicRepository.findByDataInitBetween(startDate, endDate);

        if (selicIndexes.isEmpty()) {
            throw new DataNotFoundException("Nenhum índice SELIC encontrado entre " + startDate + " e " + endDate);
        }

        BigDecimal accumulatedRate = selicIndexes.stream()
                .map(Index::getFator)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BigDecimal.ONE.add(
                accumulatedRate.divide(BigDecimal.valueOf(100), SCALE, RoundingMode.HALF_UP)
        );
    }

    private BigDecimal applyFactor(Double amount, BigDecimal factor) {
        return BigDecimal.valueOf(amount)
                .multiply(factor)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidPeriodException(startDate, endDate);
        }
    }
}
