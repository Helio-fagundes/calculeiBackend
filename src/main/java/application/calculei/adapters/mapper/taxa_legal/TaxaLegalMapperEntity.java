package application.calculei.adapters.mapper.taxa_legal;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.TaxaLegal;

public class TaxaLegalMapperEntity {

    public static Index toDomain(TaxaLegal entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static TaxaLegal toEntity(Index domain){
        return new TaxaLegal(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
