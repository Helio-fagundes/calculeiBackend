package application.calculei.usecase.ipca_tl.dto;

import java.time.LocalDate;

public record CalculateIpcaTlBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
