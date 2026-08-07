package application.calculei.usecase.tr;

import application.calculei.domain.enums.identify_enum.IdentifyFactorOrPercentual;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.value_object.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateRequest;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateTrAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculateTrAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateTrBetweenDateResponse execute(CalculateTrBetweenDateRequest request){

        validateDates(request.startDate(), request.endDate());

        validateFactor(BigDecimal.valueOf(request.amount()));

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate());

        if (listEntity.isEmpty()){
            throw new DataNotFoundException("Nenhum índice de TR encontrado para o período informado.");
        }

        BigDecimal accumulatedValue = calculateAccumulatedValue(listEntity, request.startDate(), request.endDate().minusDays(1));

        BigDecimal finalValue = calculateFinalValue(request.amount(), accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateTrBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                finalValue,
                accumulatedValue,
                IdentifyFactorOrPercentual.PERCENTUAL
        );
    }

    private void validateFactor(BigDecimal fator) {
        if (fator.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidValueException();
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate){
        if (endDate.isBefore(startDate)){
            throw new InvalidPeriodException(endDate, startDate);
        }

        if (startDate.isAfter(LocalDate.now())) {
            throw new InvalidPeriodException("inicio");
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new InvalidPeriodException("término");
        }
    }

    private BigDecimal calculateAccumulatedValue(List<Index> listEntity, LocalDate startDate, LocalDate endDate) {
        int dayAnniversary = startDate.getDayOfMonth();

        return listEntity.stream()
                .filter(index -> {
                    LocalDate dataInit = index.getDataInit();

                    if (dataInit.isBefore(startDate) || dataInit.isAfter(endDate)) {
                        return false;
                    }

                    int ultimoDiaDoMes = YearMonth.from(dataInit).lengthOfMonth();
                    int diaAlvo = Math.min(dayAnniversary, ultimoDiaDoMes);

                    return dataInit.getDayOfMonth() == diaAlvo;
                })
                .map(Index::getFator)
                .reduce(BigDecimal.ONE, BigDecimal::multiply)
                .setScale(8, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFinalValue(Double amount, BigDecimal accumulatedValue){
        return BigDecimal.valueOf(amount)
                .multiply(accumulatedValue)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
