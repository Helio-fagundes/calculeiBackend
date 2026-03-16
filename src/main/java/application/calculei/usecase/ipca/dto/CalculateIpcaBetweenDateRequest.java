package application.calculei.usecase.ipca.dto;

import java.time.LocalDate;

public record CalculateIpcaBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
