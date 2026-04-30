package application.calculei.usecase.poupanca_antiga.dto;

import java.time.LocalDate;

public record CalculatePoupAntigoBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
