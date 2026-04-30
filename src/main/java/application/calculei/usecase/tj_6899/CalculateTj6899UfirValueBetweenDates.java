package application.calculei.usecase.tj_6899;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.tj_6899.dto.CalculateTj6899BetweenDateRequest;
import application.calculei.usecase.tj_6899.dto.CalculateTj6899BetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CalculateTj6899UfirValueBetweenDates {

    private final IndexRepository repository;

    public CalculateTj6899UfirValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateTj6899BetweenDateResponse execute(CalculateTj6899BetweenDateRequest request) {

        validate(request.startDate(), request.endDate());

        Index indexInicial = fetchIndexOrThrow(request.startDate());
        Index indexFinal = fetchIndexOrThrow(request.endDate());

        BigDecimal accumulatedPercentage = calculateAccumulatedPercentage(indexInicial, indexFinal);

        BigDecimal finalValue = calculateFinalValue(request.amount(), accumulatedPercentage);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateTj6899BetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                finalValue,
                accumulatedPercentage
        );
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidPeriodException(startDate, endDate);
        }
    }

    private Index fetchIndexOrThrow(LocalDate date) {
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        try {
            return repository.findDataInit(firstDayOfMonth);
        }catch (DataNotFoundException e) {
            throw new DataNotFoundException("Índice de TJ 6899 UFIR não encontrado para a data: " + firstDayOfMonth);
        }
    }

    private BigDecimal calculateAccumulatedPercentage(Index inicial, Index finalIndex) {
        return inicial.getFator()
                .divide(finalIndex.getFator(), 6, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFinalValue(double baseValue, BigDecimal percentage) {
        return BigDecimal.valueOf(baseValue)
                .multiply(percentage)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
