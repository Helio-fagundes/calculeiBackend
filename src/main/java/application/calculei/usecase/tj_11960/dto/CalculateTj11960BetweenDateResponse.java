package application.calculei.usecase.tj_11960.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTj11960BetweenDateResponse (LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
