package application.calculei.usecase.poupanca_antiga_nova.dto;

import java.time.LocalDate;

public record CalculateIndexPoupBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
