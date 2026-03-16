package application.calculei.adapters.mapper.ipca_15;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCA15;
import application.calculei.infraestructure.entity.IndiceBC;

public class Ipca15MapperEntity {

    public static Index toDomain(IPCA15 entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IPCA15 toEntity(Index domain){
        return new IPCA15(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
