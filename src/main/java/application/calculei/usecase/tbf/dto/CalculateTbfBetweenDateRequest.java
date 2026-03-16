package application.calculei.usecase.tbf.dto;

import java.time.LocalDate;

public record CalculateTbfBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
