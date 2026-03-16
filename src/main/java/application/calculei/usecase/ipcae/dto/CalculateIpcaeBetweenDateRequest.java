package application.calculei.usecase.ipcae.dto;

import java.time.LocalDate;

public record CalculateIpcaeBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
