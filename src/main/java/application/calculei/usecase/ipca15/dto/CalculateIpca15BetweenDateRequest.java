package application.calculei.usecase.ipca15.dto;

import java.time.LocalDate;

public record CalculateIpca15BetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
