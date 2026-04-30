package application.calculei.usecase.igpdi.dto;

import java.time.LocalDate;

public record CalculateIgpdiBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
