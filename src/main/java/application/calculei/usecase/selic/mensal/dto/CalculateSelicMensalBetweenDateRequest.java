package application.calculei.usecase.selic.mensal.dto;

import java.time.LocalDate;

public record CalculateSelicMensalBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
