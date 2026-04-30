package application.calculei.usecase.selic.mensal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateSelicMensalBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue, BigDecimal accumulatedFactor) {
}
