package application.calculei.usecase.taxa_legal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTaxaLegalBetweenDateResponse(LocalDate dataInicio, LocalDate dataFinal, Long dias, BigDecimal valorFinal, BigDecimal percentualAcumulado) {
}
