package application.calculei.usecase.cdi.dto;

import java.time.LocalDate;

public record CalculateCdiBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
