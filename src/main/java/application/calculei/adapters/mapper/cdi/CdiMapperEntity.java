package application.calculei.adapters.mapper.cdi;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.CDI;
import application.calculei.infraestructure.entity.IndiceBC;

public final class CdiMapperEntity {

    private CdiMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(CDI entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static CDI toEntity(Index domain){
        return new CDI(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
