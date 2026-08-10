package application.calculei.usecase.poupanca_antiga_nova.dto;

import application.calculei.domain.enums.identify_enum.IdentifyFactorOrPercentual;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public record CalculateIndexPoupBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, Long calendarDays, DayOfWeek dayOfWeekStartDate, DayOfWeek dayOfWeekEndDate, BigDecimal finalValue, BigDecimal accumulatedFactor, IdentifyFactorOrPercentual type) {
}
