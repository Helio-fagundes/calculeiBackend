package application.calculei.usecase.ipca_tl.dto;

import java.time.LocalDate;

public record CalculateIpcaTlBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
