package application.calculei.usecase.cdi.dto;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public record CalculateCdiBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, Long calendarDays, DayOfWeek dayOfWeek, DayOfWeek dayOfWeekStartDate,BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
