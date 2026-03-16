package application.calculei.usecase.ipcbr.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpcbrBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorAcumulado, BigDecimal percentualAcumulado) {
}
