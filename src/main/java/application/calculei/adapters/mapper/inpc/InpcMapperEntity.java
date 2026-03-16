package application.calculei.adapters.mapper.inpc;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.INPC;
import application.calculei.infraestructure.entity.IndiceBC;

public class InpcMapperEntity {

    public static Index toDomain(INPC entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static INPC toEntity(Index domain){
        return new INPC(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
