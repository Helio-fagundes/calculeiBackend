package application.calculei.usecase.selic.diario.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateSelicDiarioBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue, BigDecimal accumulatedFactor) {
}
