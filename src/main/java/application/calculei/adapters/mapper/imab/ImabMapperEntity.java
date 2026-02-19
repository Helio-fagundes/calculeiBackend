package application.calculei.adapters.mapper.imab;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IMAB;
import application.calculei.infraestructure.entity.IndiceBC;

public class ImabMapperEntity {

    public static Index toDomain(IMAB entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static IMAB toEntity(Index domain){
        return new IMAB(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit(), new IndiceBC());
    }
}
