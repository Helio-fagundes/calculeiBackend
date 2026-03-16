package application.calculei.adapters.mapper.indice_tj_L6899;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Indice_TJ_L6899;

public class IndiceTjL6899MapperEntity {

    public static Index toDomain(Indice_TJ_L6899 entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static Indice_TJ_L6899 toEntity(Index domain){
        return new Indice_TJ_L6899(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
