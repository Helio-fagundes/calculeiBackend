package application.calculei.usecase.ipcae;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.valueObject.DateUtils;

import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.ipcae.dto.CalculateIpcaeBetweenDateRequest;
import application.calculei.usecase.ipcae.dto.CalculateIpcaeBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateIpcaeAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculateIpcaeAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIpcaeBetweenDateResponse execute(CalculateIpcaeBetweenDateRequest request){

        validateDates(request.startDate(), request.endDate());

        validateFactor(BigDecimal.valueOf(request.amount()));

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate());

        BigDecimal accumulatedFactor = calculateAccumulatedValue(listEntity);

        BigDecimal valueFinal = calculateFinalValue(request.amount(), accumulatedFactor);

        BigDecimal accumulatedPercentual = calculateAccumulatedPercentage(accumulatedFactor);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateIpcaeBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                valueFinal,
                accumulatedPercentual
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

    private BigDecimal calculateAccumulatedValue(List<Index> listEntity){
        return listEntity.stream()
                .map(Index::getFator)
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    private BigDecimal calculateFinalValue(Double amount, BigDecimal accumulatedFactor) {
        return BigDecimal.valueOf(amount)
                .multiply(accumulatedFactor)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateAccumulatedPercentage(BigDecimal accumulatedFactor){
        return accumulatedFactor
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(6, RoundingMode.HALF_UP);
    }
}
