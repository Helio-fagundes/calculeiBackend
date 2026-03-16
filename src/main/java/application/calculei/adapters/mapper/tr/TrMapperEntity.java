package application.calculei.adapters.mapper.tr;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.TR;

public class TrMapperEntity {

    public static Index toDomain(TR entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static TR toEntity(Index domain){
        return new TR(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
