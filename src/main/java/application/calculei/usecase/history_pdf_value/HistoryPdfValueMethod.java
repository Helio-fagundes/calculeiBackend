package application.calculei.usecase.history_pdf_value;

import application.calculei.domain.repository.HistoryPdfValuePort;
import application.calculei.usecase.exceptions.HistoryNotFoundException;
import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueRequest;
import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueResponse;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class HistoryPdfValueMethod {

    private final HistoryPdfValuePort  repository;

    public HistoryPdfValueMethod(HistoryPdfValuePort repository) {
        this.repository = repository;
    }

    public void save(HistoryPdfValueRequest request) {
        repository.save(request);
    }

    public JsonNode findByToken(String token) {
        return repository.findByToken(token)
                .orElseThrow(() -> new HistoryNotFoundException(token));
    }

}
