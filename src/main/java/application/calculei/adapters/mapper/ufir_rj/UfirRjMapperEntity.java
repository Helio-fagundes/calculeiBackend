package application.calculei.adapters.mapper.ufir_rj;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.UfirRJ;

public class UfirRjMapperEntity {

    private UfirRjMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(UfirRJ entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static UfirRJ toEntity(Index domain){
        return new UfirRJ(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
