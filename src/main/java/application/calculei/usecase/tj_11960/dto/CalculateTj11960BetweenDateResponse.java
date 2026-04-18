package application.calculei.usecase.tj_11960.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalculateTj11960BetweenDateResponse (LocalDate dataInicio, LocalDate dataFinal, Long diasContados, BigDecimal valorFinal, BigDecimal fatorAcumulado){
}
