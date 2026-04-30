package application.calculei.usecase.igpdi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIgpdiBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
