package application.calculei.adapters.mapper.ipca_15;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCA15;

public class Ipca15MapperEntity {

    public static Index toDomain(IPCA15 entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static IPCA15 toEntity(Index domain){
        return new IPCA15(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
