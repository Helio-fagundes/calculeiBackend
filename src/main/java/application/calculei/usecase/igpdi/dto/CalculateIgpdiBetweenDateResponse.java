package application.calculei.usecase.igpdi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIgpdiBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado, BigDecimal percentualAcumulado) {
}
