package application.calculei.usecase.inpc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateInpcBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado, BigDecimal percentualAcumulado) {
}
