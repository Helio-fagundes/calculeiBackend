package application.calculei.usecase.cdi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateCdiBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
