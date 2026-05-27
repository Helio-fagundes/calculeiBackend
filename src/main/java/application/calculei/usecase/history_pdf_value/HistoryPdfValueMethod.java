package application.calculei.usecase.history_pdf_value;

import application.calculei.domain.repository.HistoryPdfValuePort;
import application.calculei.usecase.exceptions.HistoryNotFoundException;
import application.calculei.usecase.exceptions.ValueNullOrEmptyException;
import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueRequest;
import com.fasterxml.jackson.databind.JsonNode;


public class HistoryPdfValueMethod {

    private final HistoryPdfValuePort  repository;

    public HistoryPdfValueMethod(HistoryPdfValuePort repository) {
        this.repository = repository;
    }

    public String save(HistoryPdfValueRequest request) {
        validateToken(request);
        repository.save(request);
        return request.token();
    }

    public JsonNode findByToken(String token) {
        if (token.isEmpty()) {
            throw new ValueNullOrEmptyException("Url");
        }
        return repository.findByToken(token)
                .orElseThrow(() -> new HistoryNotFoundException(token));
    }

    private void validateToken(HistoryPdfValueRequest request) {
        if (request.token() == null || request.token().isEmpty()) {
            throw new ValueNullOrEmptyException("Token");
        }
    }
}
