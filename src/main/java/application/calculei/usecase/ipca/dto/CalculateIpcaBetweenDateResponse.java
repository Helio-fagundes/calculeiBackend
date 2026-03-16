package application.calculei.usecase.ipca.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpcaBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado, BigDecimal percentualAcumulado) {
}
