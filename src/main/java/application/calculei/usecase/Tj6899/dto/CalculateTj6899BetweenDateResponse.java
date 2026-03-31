package application.calculei.usecase.Tj6899.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTj6899BetweenDateResponse(LocalDate dataInicio, LocalDate dataFinal, int diasContados, BigDecimal valorFinal, BigDecimal fatorAcumulado) {
}
