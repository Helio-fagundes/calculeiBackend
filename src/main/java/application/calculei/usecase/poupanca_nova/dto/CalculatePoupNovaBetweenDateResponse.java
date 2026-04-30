package application.calculei.usecase.poupanca_nova.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculatePoupNovaBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue, BigDecimal accumulatedFactor) {
}
