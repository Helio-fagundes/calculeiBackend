package application.calculei.usecase.history_pdf_value.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;

public record HistoryPdfValueRequest(LocalDate date, String token, JsonNode json) {
}
