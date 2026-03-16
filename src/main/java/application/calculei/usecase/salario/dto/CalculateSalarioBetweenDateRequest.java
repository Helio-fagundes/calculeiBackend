package application.calculei.usecase.salario.dto;

import java.time.LocalDate;

public record CalculateSalarioBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
