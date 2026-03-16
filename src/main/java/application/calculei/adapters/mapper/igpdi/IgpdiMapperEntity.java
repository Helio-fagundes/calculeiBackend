package application.calculei.adapters.mapper.igpdi;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IGPDI;
import application.calculei.infraestructure.entity.IndiceBC;

public class IgpdiMapperEntity {

    public static Index toDomain(IGPDI entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IGPDI toEntity(Index domain){
        return new IGPDI(domain.getId(),  domain.getFator(),domain.getDataInit(), new IndiceBC());
    }
}
