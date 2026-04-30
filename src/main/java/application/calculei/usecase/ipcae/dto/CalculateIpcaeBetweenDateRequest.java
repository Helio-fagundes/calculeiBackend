package application.calculei.usecase.ipcae.dto;

import java.time.LocalDate;

public record CalculateIpcaeBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
