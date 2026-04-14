package application.calculei.adapters.mapper.history_pdf_value;

import application.calculei.domain.models.HistoryPdfValueDomain;
import application.calculei.infraestructure.entity.HistoryPdfValueEntity;

public class HistoryPdfValueMapperEntity {

    public static HistoryPdfValueDomain toDomain(HistoryPdfValueEntity entity) {
        return new HistoryPdfValueDomain(entity.getId(), entity.getDate(), entity.getJson(), entity.getToken());
    }

    public static HistoryPdfValueEntity toEntity(HistoryPdfValueDomain domain) {
        return new HistoryPdfValueEntity(domain.getId(), domain.getDate(), domain.getJson(), domain.getToken());
    }
}
