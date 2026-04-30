package application.calculei.usecase.ipcae.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpcaeBetweenDateResponse(LocalDate startDate, LocalDate endDate, long businessDays, BigDecimal valueFinal, BigDecimal accumulatedFactor) {
}
