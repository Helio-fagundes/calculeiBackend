package application.calculei.usecase.cdi.dto;

import java.time.LocalDate;

public record CalculateBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
