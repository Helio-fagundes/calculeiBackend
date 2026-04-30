package application.calculei.usecase.ipca15.dto;

import java.time.LocalDate;

public record CalculateIpca15BetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
