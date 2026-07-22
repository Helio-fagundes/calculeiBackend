package application.calculei.usecase.igpdi;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.value_object.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.igpdi.dto.CalculateIgpdiBetweenDateRequest;
import application.calculei.usecase.igpdi.dto.CalculateIgpdiBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateIgpdiAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculateIgpdiAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIgpdiBetweenDateResponse execute(CalculateIgpdiBetweenDateRequest request){

        validateDates(request.startDate(), request.endDate());

        validateFactor(BigDecimal.valueOf(request.amount()));

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate());

        if (listEntity.isEmpty()) {
            throw new DataNotFoundException("Nenhum índice IGPDI encontrado para o período informado.");
        }

        BigDecimal accumulatedValue = calculateAccumulatedFactor(listEntity);

        BigDecimal valueFinal = calculateFinalValue(request.amount(), accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateIgpdiBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                valueFinal,
                accumulatedValue
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

    private BigDecimal calculateAccumulatedFactor(List<Index> indexes) {
        return indexes.stream()
                .map(Index::getFator)
                .reduce(BigDecimal.ONE, BigDecimal::multiply)
                .setScale(6, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFinalValue(Double amount, BigDecimal accumulatedFactor) {
        return BigDecimal.valueOf(amount)
                .multiply(accumulatedFactor)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
