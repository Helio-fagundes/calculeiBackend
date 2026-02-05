package application.calculei.adapters.mapper.ipca_tj;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCA_TJ;

public class IpcaTjMapperEntity {

    public static Index toDomain(IPCA_TJ entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static IPCA_TJ toEntity(Index domain){
        return new IPCA_TJ(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
