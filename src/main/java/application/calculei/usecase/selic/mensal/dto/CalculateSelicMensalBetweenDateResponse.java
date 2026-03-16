package application.calculei.usecase.selic.mensal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateSelicMensalBetweenDateResponse(LocalDate dataInicio, LocalDate dataFim, Long dias, BigDecimal valorFinal, BigDecimal percentualAcumulado) {
}
