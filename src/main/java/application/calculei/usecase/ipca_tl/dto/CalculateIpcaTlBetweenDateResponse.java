package application.calculei.usecase.ipca_tl.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpcaTlBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
