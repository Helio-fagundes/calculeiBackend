package application.calculei.usecase.salario.dto;

import java.time.LocalDate;

public record CalculateSalarioBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
