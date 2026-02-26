package application.calculei.usecase.igpdi.dto;

import java.time.LocalDate;

public record CalculateIgpdiBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
