package application.calculei.usecase.ipcbr.dto;

import java.time.LocalDate;

public record CalculateIpcbrBetweenDateRequest(Double valor, LocalDate dateInit, LocalDate dateFim) {
}
