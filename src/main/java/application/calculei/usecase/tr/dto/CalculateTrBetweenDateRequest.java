package application.calculei.usecase.tr.dto;

import java.time.LocalDate;

public record CalculateTrBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
