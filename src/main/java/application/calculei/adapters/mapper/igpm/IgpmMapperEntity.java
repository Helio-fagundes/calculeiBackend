package application.calculei.adapters.mapper.igpm;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IGPM;
import application.calculei.infraestructure.entity.IndiceBC;

public class IgpmMapperEntity {

    private IgpmMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(IGPM entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IGPM toEntity(Index domain){
        return new IGPM(domain.getId(),  domain.getFator(),domain.getDataInit(), new IndiceBC());
    }
}
