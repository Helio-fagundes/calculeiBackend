package application.calculei.usecase.tr.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTrBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorFinal, BigDecimal percentualAcumulado) {
}
