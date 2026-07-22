package application.calculei.usecase.poupanca_antiga_nova;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.value_object.DateUtils;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.poupanca_antiga_nova.dto.CalculateIndexPoupBetweenDateRequest;
import application.calculei.usecase.poupanca_antiga_nova.dto.CalculateIndexPoupBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculatePoupNovaAndAntigaAccumulatedValueByPeriod {

    private final IndexRepository poupAntiga;
    private final IndexRepository poupNova;

    public CalculatePoupNovaAndAntigaAccumulatedValueByPeriod(IndexRepository poupAntiga, IndexRepository poupNova) {
        this.poupAntiga = poupAntiga;
        this.poupNova = poupNova;
    }

    public CalculateIndexPoupBetweenDateResponse execute(CalculateIndexPoupBetweenDateRequest request) {

        validateDates(request.startDate(), request.endDate());

        validateFactor(BigDecimal.valueOf(request.amount()));

        BigDecimal accumulatedFactor = calculateHybridFactor(request.startDate(), request.endDate());

        BigDecimal finalPercentage = calculateAccumulatedPercentage(accumulatedFactor);

        BigDecimal finalValue = applyFactor(request.amount(), accumulatedFactor);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        return new CalculateIndexPoupBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                finalValue,
                finalPercentage
        );
    }

    private BigDecimal calculateHybridFactor(LocalDate startDate, LocalDate endDate) {
        LocalDate realCalculationEnd = endDate.minusMonths(1);
        List<Index> indicesAntiga = poupAntiga.findByDataInitBetween(startDate, realCalculationEnd);
        List<Index> indicesNova = poupNova.findByDataInitBetween(startDate, realCalculationEnd);

        BigDecimal totalFactor = BigDecimal.ONE;
        LocalDate current = startDate;

        while (!current.isAfter(realCalculationEnd)) {
            BigDecimal monthFactor;

            if (current.isBefore(LocalDate.of(2012, 6, 1))) {
                monthFactor = findFactorByMonthYear(indicesAntiga, current);
            } else {
                monthFactor = findFactorByMonthYear(indicesNova, current);
            }

            totalFactor = totalFactor.multiply(monthFactor);
            current = current.plusMonths(1);
        }
        return totalFactor.setScale(10, RoundingMode.HALF_UP);
    }

    private BigDecimal findFactorByMonthYear(List<Index> list, LocalDate date) {
        return list.stream()
                .filter(i -> i.getDataInit().getMonth() == date.getMonth() &&
                        i.getDataInit().getYear() == date.getYear())
                .map(Index::getFator)
                .findFirst()
                .orElse(BigDecimal.ONE);
    }

    private BigDecimal applyFactor(Double amount, BigDecimal factor) {
        return BigDecimal.valueOf(amount)
                .multiply(factor)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateAccumulatedPercentage(BigDecimal accumulatedFactor){
        return accumulatedFactor
                .subtract(BigDecimal.ONE)
                .setScale(6, RoundingMode.HALF_UP);
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
}
