package application.calculei.adapters.mapper.igpm;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IGPM;

public class IgpmMapperEntity {

    public static Index toDomain(IGPM entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static IGPM toEntity(Index domain){
        return new IGPM(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
