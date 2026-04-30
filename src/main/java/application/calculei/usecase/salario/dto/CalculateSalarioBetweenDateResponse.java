package application.calculei.usecase.salario.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateSalarioBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue,BigDecimal accumulatedFactor) {
}
