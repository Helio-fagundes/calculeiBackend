package application.calculei.usecase.ipca15.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpca15BetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
