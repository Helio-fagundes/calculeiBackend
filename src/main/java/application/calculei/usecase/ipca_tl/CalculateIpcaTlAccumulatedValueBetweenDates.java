package application.calculei.usecase.ipca_tl;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.valueObject.DateUtils;

import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.ipca_tl.dto.CalculateIpcaTlBetweenDateRequest;
import application.calculei.usecase.ipca_tl.dto.CalculateIpcaTlBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateIpcaTlAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculateIpcaTlAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIpcaTlBetweenDateResponse execute(CalculateIpcaTlBetweenDateRequest request){
        validatedDate(request.startDate(), request.endDate());

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate());

        BigDecimal accumulatedValue = accumulatedFactor(listEntity);

        BigDecimal valueFinal = calculateFinalValue(request.amount(), accumulatedValue);

        BigDecimal accumulatedPercentual = calculateAccumulatedPercentage(accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateIpcaTlBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                valueFinal,
                accumulatedPercentual
        );
    }

    private void validatedDate(LocalDate startDate, LocalDate endDate){
        if (endDate.isBefore(startDate)){
            throw new InvalidPeriodException(startDate, endDate);
        }
    }

    private BigDecimal accumulatedFactor(List<Index>  listEntity){
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
