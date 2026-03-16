package application.calculei.usecase.salario.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateSalarioBetweenDateResponse(LocalDate dataInicio, LocalDate dataFinal, Long dias, BigDecimal valorFinal,BigDecimal fatorAcumulado) {
}
