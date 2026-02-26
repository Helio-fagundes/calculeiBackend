package application.calculei.usecase.cdi.dto;

import java.time.LocalDate;

public record CalculateCdiBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
