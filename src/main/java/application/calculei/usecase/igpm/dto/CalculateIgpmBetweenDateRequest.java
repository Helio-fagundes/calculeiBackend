package application.calculei.usecase.igpm.dto;

import java.time.LocalDate;

public record CalculateIgpmBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
