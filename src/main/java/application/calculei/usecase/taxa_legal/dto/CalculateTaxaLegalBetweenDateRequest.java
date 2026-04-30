package application.calculei.usecase.taxa_legal.dto;

import java.time.LocalDate;

public record CalculateTaxaLegalBetweenDateRequest(Double amount, LocalDate startDate, LocalDate endDate) {
}
