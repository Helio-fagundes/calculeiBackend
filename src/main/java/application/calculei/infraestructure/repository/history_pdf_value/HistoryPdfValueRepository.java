package application.calculei.infraestructure.repository.history_pdf_value;

import application.calculei.infraestructure.entity.HistoryPdfValueEntity;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryPdfValueRepository extends JpaRepository<HistoryPdfValueEntity, Long> {

    Optional<HistoryPdfValueEntity> findByToken(String token);

}
