package application.calculei.usecase.poupanca_antiga.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculatePoupAntigoBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays , BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
