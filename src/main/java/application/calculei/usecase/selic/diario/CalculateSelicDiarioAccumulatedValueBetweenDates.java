package application.calculei.usecase.selic.diario;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateRequest;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateSelicDiarioAccumulatedValueBetweenDates {

    private final IndexRepository  repository;

    public CalculateSelicDiarioAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateSelicDiarioBetweenDateResponse execute(CalculateSelicDiarioBetweenDateRequest request) {

        validateDates(request.startDate(), request.endDate());

        validateFactor(BigDecimal.valueOf(request.amount()));

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate());

        if (listEntity.isEmpty()) {
            throw new DataNotFoundException("Nenhum índice de Selic Diário encontrado para o período informado.");
        }

        BigDecimal accumulatedValue = calculateAccumulatedValue(listEntity);

        BigDecimal finalValue = calculateFinalValue(request.amount(), accumulatedValue);

        BigDecimal accumulatedPercentage = calculateAccumulatedPercentage(accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateSelicDiarioBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                finalValue,
                accumulatedPercentage);
    }

    private void validateFactor(BigDecimal fator) {
        if (fator.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O Fator deve ser maior que zero");
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate){
        if (endDate.isBefore(startDate)){
            throw new InvalidPeriodException(endDate, startDate);
        }

        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data atual.");
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de término não pode ser posterior à data atual.");
        }
    }

    private BigDecimal calculateAccumulatedValue(List<Index> listEntity) {
        return listEntity.stream()
                .map(Index::getFator)
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    private BigDecimal calculateFinalValue(Double amount, BigDecimal accumulatedFactor) {
        return BigDecimal.valueOf(amount)
                .multiply(accumulatedFactor)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateAccumulatedPercentage(BigDecimal accumulatedFactor) {
        return accumulatedFactor.subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
