package application.calculei.usecase.poupanca_antiga.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculatePoupAntigoBetweenDateResponse(LocalDate dataInicio, LocalDate dataFinal, Long dias , BigDecimal valorFinal, BigDecimal fatorAcumulado) {
}
