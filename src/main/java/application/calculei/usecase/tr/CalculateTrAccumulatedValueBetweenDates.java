package application.calculei.usecase.tr;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateRequest;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateTrAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculateTrAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateTrBetweenDateResponse execute(CalculateTrBetweenDateRequest request){

        validatedDate(request.startDate(), request.endDate());

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate());

       BigDecimal accumulatedValue = calculateAccumulatedValue(listEntity);

       BigDecimal finalValue = calculateFinalValue(request.amount(), accumulatedValue);

       BigDecimal accumulatedPercentage = calculateAccumulatedPercentage(accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateTrBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                finalValue,
                accumulatedValue
        );
    }

    private void validatedDate(LocalDate startDate, LocalDate endDate){
        if (endDate.isBefore(startDate)){
            throw new InvalidPeriodException(startDate, endDate);
        }
    }

    private BigDecimal calculateAccumulatedValue(List<Index> listEntity){
        return listEntity.stream()
                .map(Index::getFator)
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    private BigDecimal calculateFinalValue(Double amount, BigDecimal accumulatedValue){
        return BigDecimal.valueOf(amount)
                .multiply(accumulatedValue)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateAccumulatedPercentage(BigDecimal accumulatedFactor){
        return accumulatedFactor
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(6, RoundingMode.HALF_UP);
    }
}
