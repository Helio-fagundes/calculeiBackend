package application.calculei.usecase.ipca.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpcaBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
