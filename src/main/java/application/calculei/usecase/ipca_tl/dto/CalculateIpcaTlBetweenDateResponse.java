package application.calculei.usecase.ipca_tl.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateIpcaTlBetweenDateResponse(LocalDate dataInicial, LocalDate dataFinal, Long dias, BigDecimal valorFinal, BigDecimal fatorAcumulado) {
}
