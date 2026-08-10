package application.calculei.usecase.tr.dto;

import application.calculei.domain.enums.identify_enum.IdentifyFactorOrPercentual;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTrBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal finalValue, BigDecimal accumulatedValue, IdentifyFactorOrPercentual type) {
}
