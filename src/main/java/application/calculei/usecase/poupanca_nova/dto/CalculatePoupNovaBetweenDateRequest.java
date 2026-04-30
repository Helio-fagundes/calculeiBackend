package application.calculei.usecase.poupanca_nova.dto;

import java.time.LocalDate;

public record CalculatePoupNovaBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
