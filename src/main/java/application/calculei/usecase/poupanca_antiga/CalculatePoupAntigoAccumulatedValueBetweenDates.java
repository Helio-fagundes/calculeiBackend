package application.calculei.usecase.poupanca_antiga;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.value_object.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.poupanca_antiga.dto.CalculatePoupAntigoBetweenDateRequest;
import application.calculei.usecase.poupanca_antiga.dto.CalculatePoupAntigoBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculatePoupAntigoAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculatePoupAntigoAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculatePoupAntigoBetweenDateResponse execute(CalculatePoupAntigoBetweenDateRequest request){

        validateDates(request.startDate(), request.endDate());

        validateFactor(BigDecimal.valueOf(request.amount()));

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate().minusMonths(1));

        BigDecimal accumulatedValue = BigDecimal.ONE;

        if (!listEntity.isEmpty()) {
            accumulatedValue = calculateAccumulatedValue(listEntity, request.startDate());
        }

        if (listEntity.isEmpty()) {
            throw new DataNotFoundException("Nenhum índice de Poupança antiga encontrado para o período informado.");
        }

        BigDecimal valueFinal = calculateFinalValue(request.amount(), accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculatePoupAntigoBetweenDateResponse(
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

    private BigDecimal calculateAccumulatedValue(List<Index> listEntity, LocalDate startDate){
        int anniversaryDay = startDate.getDayOfMonth();

        if (anniversaryDay > 28) {
            anniversaryDay = 1;
        }

        final int targetDay = anniversaryDay;

        return listEntity.stream()
                .filter(index -> index.getDataInit().getDayOfMonth() == targetDay)
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
