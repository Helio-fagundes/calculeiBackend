package application.calculei.usecase.selic.diario.dto;

import application.calculei.domain.enums.identify_enum.IdentifyFactorOrPercentual;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public record CalculateSelicDiarioBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, Long calendarDays, DayOfWeek dayOfWeekStartDate, DayOfWeek dayOfWeekEndDate, BigDecimal finalValue, BigDecimal accumulatedFactor, IdentifyFactorOrPercentual type) {
}
