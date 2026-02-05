package application.calculei.adapters.mapper.igpdi;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IGPDI;

public class IgpdiMapperEntity {

    public static Index toDomain(IGPDI entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static IGPDI toEntity(Index domain){
        return new IGPDI(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(),domain.getDataInit());
    }
}
