package application.calculei.adapters.mapper.indice_tj_l11960_selic;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.IndiceTjl11960Selic;

public class IndiceTjL11960SelicMapperEntity {

    private IndiceTjL11960SelicMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(IndiceTjl11960Selic entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IndiceTjl11960Selic toEntity(Index domain){
        return new IndiceTjl11960Selic(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
