package application.calculei.usecase.taxa_legal;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.value_object.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateRequest;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateTaxaLegalAccumulatedValueBetweenDates {

    private final IndexRepository repository;

    public CalculateTaxaLegalAccumulatedValueBetweenDates(IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateTaxaLegalBetweenDateResponse execute(CalculateTaxaLegalBetweenDateRequest request){

        validateDates(request.startDate(), request.endDate());

        validateFactor(BigDecimal.valueOf(request.amount()));

        LocalDate queryStartDate = request.startDate().withDayOfMonth(1);
        LocalDate queryEndDate = YearMonth.from(request.endDate()).atEndOfMonth();

        List<Index> listEntity = repository.findByDataInitBetween(queryStartDate, queryEndDate);

        if (listEntity.isEmpty()){
            throw new DataNotFoundException("Nenhum índice de Taxa Legal encontrado para o período informado.");
        }

        BigDecimal accumulatedValue = calculateAccumulatedValue(listEntity, request.startDate(), request.endDate());

        BigDecimal finalValue = calculateFinalValue(request.amount(), accumulatedValue);

        long businessDays = DateUtils.businessDays(request.startDate(), request.endDate());

        Long calendarDays = ChronoUnit.DAYS.between(request.startDate(), request.endDate());

        DayOfWeek dayOfWeek = request.endDate().getDayOfWeek();

        return new CalculateTaxaLegalBetweenDateResponse(
                request.startDate(),
                request.endDate(),
                businessDays,
                calendarDays,
                dayOfWeek,
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
        LocalDate inicioMetodologia = LocalDate.of(2024, 8, 30);
        if (startDate.isBefore(inicioMetodologia)) {
            throw new InvalidPeriodException("A Taxa Legal só pode ser aplicada a partir de 30/08/2024.");
        }

        if (!endDate.isAfter(startDate)){
            throw new InvalidPeriodException(endDate, startDate);
        }

        if (startDate.isAfter(LocalDate.now())) {
            throw new InvalidPeriodException("inicio");
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new InvalidPeriodException("término");
        }
    }

    private BigDecimal calculateAccumulatedValue(List<Index> listEntity, LocalDate startDate, LocalDate endDate) {
        BigDecimal sumOfRates = BigDecimal.ZERO;

        for (Index index : listEntity) {
            LocalDate dataIndex = index.getDataInit();
            YearMonth anoMes = YearMonth.from(dataIndex);

            LocalDate primeiroDiaMes = anoMes.atDay(1);
            LocalDate ultimoDiaMes = anoMes.atEndOfMonth();
            int diasMes = anoMes.lengthOfMonth();

            LocalDate trechoInicio = startDate.isAfter(primeiroDiaMes) ? startDate : primeiroDiaMes;

            LocalDate trechoFimExclusive = endDate.isBefore(ultimoDiaMes.plusDays(1))
                    ? endDate
                    : ultimoDiaMes.plusDays(1);

            if (trechoInicio.isBefore(trechoFimExclusive)) {

                long diasPcalculo = ChronoUnit.DAYS.between(trechoInicio, trechoFimExclusive);

                if (diasPcalculo > 0) {
                    BigDecimal taxaDecimal = index.getFator().subtract(BigDecimal.ONE);

                    if (taxaDecimal.compareTo(BigDecimal.ZERO) < 0) {
                        taxaDecimal = BigDecimal.ZERO;
                    }

                    BigDecimal jurosProRata = taxaDecimal
                            .multiply(BigDecimal.valueOf(diasPcalculo))
                            .divide(BigDecimal.valueOf(diasMes), 16, RoundingMode.HALF_UP);

                    sumOfRates = sumOfRates.add(jurosProRata);
                }
            }
        }

        return BigDecimal.ONE.add(sumOfRates).setScale(8, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFinalValue(Double amount, BigDecimal accumulatedValue){
        return BigDecimal.valueOf(amount)
                .multiply(accumulatedValue)
                .setScale(2, RoundingMode.HALF_UP);
    }
}