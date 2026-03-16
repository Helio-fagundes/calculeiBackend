package application.calculei.usecase.poupanca_antiga.dto;

import java.time.LocalDate;

public record CalculatePoupAntigoBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
