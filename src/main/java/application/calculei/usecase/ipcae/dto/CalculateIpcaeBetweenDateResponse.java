package application.calculei.usecase.ipcae.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpcaeBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado, BigDecimal percentualAcumulado) {
}
