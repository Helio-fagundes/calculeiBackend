package application.calculei.usecase.tbf.dto;

import java.time.LocalDate;

public record CalculateTbfBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
