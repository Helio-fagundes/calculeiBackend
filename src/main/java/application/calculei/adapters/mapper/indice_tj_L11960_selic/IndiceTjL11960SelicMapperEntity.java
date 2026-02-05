package application.calculei.adapters.mapper.indice_tj_L11960_selic;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.Indice_TJ_L11960_Selic;

public class IndiceTjL11960SelicMapperEntity {

    public static Index toDomain(Indice_TJ_L11960_Selic entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static Indice_TJ_L11960_Selic toEntity(Index domain){
        return new Indice_TJ_L11960_Selic(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
