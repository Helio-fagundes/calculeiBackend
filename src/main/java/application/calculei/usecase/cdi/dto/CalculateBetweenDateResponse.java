package application.calculei.usecase.cdi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado) {
}
