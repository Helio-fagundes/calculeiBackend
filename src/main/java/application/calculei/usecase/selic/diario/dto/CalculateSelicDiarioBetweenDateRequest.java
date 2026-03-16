package application.calculei.usecase.selic.diario.dto;

import java.time.LocalDate;

public record CalculateSelicDiarioBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
