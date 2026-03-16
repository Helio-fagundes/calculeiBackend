package application.calculei.usecase.igpm.dto;

import java.time.LocalDate;

public record CalculateIgpmBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
