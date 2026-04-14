package application.calculei.adapters.gateway.history_pdf_value;

import application.calculei.domain.repository.HistoryPdfValuePort;
import application.calculei.infraestructure.entity.HistoryPdfValueEntity;
import application.calculei.infraestructure.repository.history_pdf_value.HistoryPdfValueRepository;
import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;

public class HistoryJpaRepository implements HistoryPdfValuePort {

    private final HistoryPdfValueRepository repository;

    public HistoryJpaRepository(HistoryPdfValueRepository repository){
        this.repository = repository;
    }

    @Override
    public Optional<JsonNode> findByToken(String token) {
        return repository.findByToken(token)
                .map(entity -> {
                    try {
                        return new ObjectMapper().readValue(entity.getJson(), JsonNode.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao converter JSON", e);
                    }
                });
    }

    @Override
    public void save(HistoryPdfValueRequest request) {
        HistoryPdfValueEntity entity = new HistoryPdfValueEntity();
        entity.setToken(request.token());
        entity.setDate(LocalDate.now());
        entity.setJson(request.json().toString());
        repository.save(entity);
    }
}
