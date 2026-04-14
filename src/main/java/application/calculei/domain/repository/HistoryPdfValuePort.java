package application.calculei.domain.repository;

import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueRequest;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public interface HistoryPdfValuePort {

        Optional<JsonNode> findByToken(String token);
        void save(HistoryPdfValueRequest request);
}
