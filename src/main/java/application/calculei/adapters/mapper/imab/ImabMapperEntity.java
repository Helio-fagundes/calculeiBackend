package application.calculei.adapters.mapper.imab;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IMAB;
import application.calculei.infraestructure.entity.IndiceBC;

public class ImabMapperEntity {

    public static Index toDomain(IMAB entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IMAB toEntity(Index domain){
        return new IMAB(domain.getId(),  domain.getFator(),domain.getDataInit(), new IndiceBC());
    }
}
