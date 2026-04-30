package application.calculei.usecase.tj_6899.dto;

import java.time.LocalDate;

public record CalculateTj6899BetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
