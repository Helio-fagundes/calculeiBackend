package application.calculei.usecase.tbf.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTbfBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue, BigDecimal accumulatedFactor) {
}
