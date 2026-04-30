package application.calculei.usecase.ipca.dto;

import java.time.LocalDate;

public record CalculateIpcaBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
