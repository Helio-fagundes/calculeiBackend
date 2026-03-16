package application.calculei.adapters.mapper.selic;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.SelicDiario;
import application.calculei.infraestructure.entity.SelicMensal;

public class SelicDiarioMapperEntity {

    public static Index toDomain(SelicDiario entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static SelicDiario toEntity(Index domain){
        return new SelicDiario(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
