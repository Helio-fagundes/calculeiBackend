package application.calculei.usecase.igpm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIgpmBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
