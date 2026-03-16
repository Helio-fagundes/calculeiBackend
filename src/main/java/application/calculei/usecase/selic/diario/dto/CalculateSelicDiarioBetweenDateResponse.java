package application.calculei.usecase.selic.diario.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateSelicDiarioBetweenDateResponse(LocalDate dataInicio, LocalDate dataFinal, Long dias, BigDecimal valorFinal, BigDecimal percentualAcumulado) {
}
