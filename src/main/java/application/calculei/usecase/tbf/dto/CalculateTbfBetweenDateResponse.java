package application.calculei.usecase.tbf.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTbfBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado, BigDecimal percentualAcumulado) {
}
