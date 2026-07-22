package application.calculei.usecase.tr;

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
import java.util.List;

public class CalculateTrAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculateTrAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateTrBetweenDateResponse execute(CalculateTrBetweenDateRequest request){

        validateDates(request.startDate(), request.endDate());

        validateFactor(BigDecimal.valueOf(request.amount()));

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate().minusDays(1));

        if (listEntity.isEmpty()){
            throw new DataNotFoundException("Nenhum índice de TR encontrado para o período informado.");
        }

        BigDecimal accumulatedValue = calculateAccumulatedValue(listEntity);

        BigDecimal finalValue = calculateFinalValue(request.amount(), accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateTrBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                finalValue,
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

    private BigDecimal calculateAccumulatedValue(List<Index> listEntity){
        return listEntity.stream()
                .filter(index -> index.getDataInit().getDayOfMonth() == 1)
                .map(Index::getFator)
                .reduce(BigDecimal.ONE, (acc, fator) -> acc.multiply(fator).setScale(6, RoundingMode.HALF_UP));
    }

    private BigDecimal calculateFinalValue(Double amount, BigDecimal accumulatedValue){
        BigDecimal value = new BigDecimal(String.valueOf(amount));
        return value
                .multiply(accumulatedValue)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
