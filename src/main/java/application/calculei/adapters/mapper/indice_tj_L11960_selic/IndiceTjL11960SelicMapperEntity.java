package application.calculei.adapters.mapper.indice_tj_L11960_selic;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Indice_TJ_L11960_Selic;

public class IndiceTjL11960SelicMapperEntity {

    private IndiceTjL11960SelicMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(Indice_TJ_L11960_Selic entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static Indice_TJ_L11960_Selic toEntity(Index domain){
        return new Indice_TJ_L11960_Selic(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
