package application.calculei.usecase.tj_6899.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTj6899BetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue, BigDecimal accumulatedValue) {
}
