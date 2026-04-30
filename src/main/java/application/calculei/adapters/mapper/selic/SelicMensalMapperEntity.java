package application.calculei.adapters.mapper.selic;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.SelicMensal;

public class SelicMensalMapperEntity {

    private SelicMensalMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(SelicMensal entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static SelicMensal toEntity(Index domain){
        return new SelicMensal(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
