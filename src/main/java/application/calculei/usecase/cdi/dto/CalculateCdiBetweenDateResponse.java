package application.calculei.usecase.cdi.dto;

import application.calculei.domain.enums.identify_enum.IdentifyFactorOrPercentual;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public record CalculateCdiBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, Long calendarDays, DayOfWeek dayOfWeekStartDate, DayOfWeek dayOfWeekEndDate, BigDecimal valueFinal, BigDecimal accumulatedFactor, IdentifyFactorOrPercentual type) {
}
