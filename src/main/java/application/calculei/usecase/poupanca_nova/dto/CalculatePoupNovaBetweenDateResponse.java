package application.calculei.usecase.poupanca_nova.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculatePoupNovaBetweenDateResponse(LocalDate dataInicio, LocalDate dataFinal, Long dias, BigDecimal valorAcumulado, BigDecimal fatorAcumulado) {
}
