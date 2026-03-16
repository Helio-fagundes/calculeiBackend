package application.calculei.usecase.igpm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIgpmBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado, BigDecimal percentualAcumulado) {
}
