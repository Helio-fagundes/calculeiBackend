package application.calculei.usecase.taxa_legal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTaxaLegalBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue, BigDecimal accumulatedFactor) {
}
