package application.calculei.usecase.poupanca_nova.dto;

import java.time.LocalDate;

public record CalculatePoupNovaBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
