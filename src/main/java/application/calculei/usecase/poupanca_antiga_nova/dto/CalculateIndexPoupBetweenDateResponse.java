package application.calculei.usecase.poupanca_antiga_nova.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIndexPoupBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue, BigDecimal accumulatedFactor) {
}
