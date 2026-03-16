package application.calculei.usecase.selic.mensal.dto;

import java.time.LocalDate;

public record CalculateSelicMensalBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
