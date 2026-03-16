package application.calculei.usecase.ipca15.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpca15BetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado, BigDecimal percentualAcumulado) {
}
