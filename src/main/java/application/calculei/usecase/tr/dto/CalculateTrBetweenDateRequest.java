package application.calculei.usecase.tr.dto;

import java.time.LocalDate;

public record CalculateTrBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
