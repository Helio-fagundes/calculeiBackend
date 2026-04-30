package application.calculei.adapters.mapper.taxa_legal;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.TaxaLegal;

public class TaxaLegalMapperEntity {

    private TaxaLegalMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(TaxaLegal entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static TaxaLegal toEntity(Index domain){
        return new TaxaLegal(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
