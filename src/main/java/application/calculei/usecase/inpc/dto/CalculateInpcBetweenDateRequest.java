package application.calculei.usecase.inpc.dto;

import java.time.LocalDate;

public record CalculateInpcBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
