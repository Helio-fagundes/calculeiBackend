package application.calculei.usecase.cdi;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateCdiAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculateCdiAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateCdiBetweenDateResponse execute(CalculateCdiBetweenDateRequest request){

        validateDates(request.startDate(), request.endDate());

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate());

        if (listEntity.isEmpty()) {
            throw new DataNotFoundException("Nenhum índice CDI encontrado para o período informado.");
        }

        BigDecimal accumulatedValue = calculateAccumulatedFactor(listEntity);

        BigDecimal finalValue = calculateFinalValue(request.amount(), accumulatedValue);

        BigDecimal accumulatedPercentage = calculateAccumulatedPercentage(accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateCdiBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                finalValue,
                accumulatedPercentage
                );
    }

    private void validateDates(LocalDate startDate, LocalDate endDate){
        if (startDate.isBefore(endDate)){
            throw new InvalidPeriodException(startDate, endDate);
        }
    }

    private BigDecimal calculateAccumulatedFactor(List<Index> indexes) {
        return indexes.stream()
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
