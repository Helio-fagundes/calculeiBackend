package application.calculei.usecase.poupanca_nova;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateRequest;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculatePoupNovaAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculatePoupNovaAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculatePoupNovaBetweenDateResponse execute(CalculatePoupNovaBetweenDateRequest request){

        validatedDate(request.startDate(), request.endDate());

        List<Index> listEntity = repository.findByDataInitBetween(request.startDate(), request.endDate().minusMonths(1));

        if (listEntity.isEmpty()) {
            throw new DataNotFoundException("Nenhum índice de Poupança Nova encontrado para o período informado.");
        }

        BigDecimal accumulatedFactor = calculateAccumulatedValue(listEntity);

        BigDecimal finalValue = calculateFinalValue(request.amount(), accumulatedFactor);

        BigDecimal percentualAccumulated = calculateAccumulatedPercentage(accumulatedFactor);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());


        return new CalculatePoupNovaBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                finalValue,
                percentualAccumulated
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
