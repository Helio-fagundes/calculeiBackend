package application.calculei.adapters.mapper.indice_tj_L11960;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Indice_TJ_L11960;

public class IndiceTjL11960MapperEntity {

    public static Index toDomain(Indice_TJ_L11960 entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static Indice_TJ_L11960 toEntity(Index domain){
        return new Indice_TJ_L11960(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
