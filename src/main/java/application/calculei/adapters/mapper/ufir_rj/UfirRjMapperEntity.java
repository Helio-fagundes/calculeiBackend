package application.calculei.adapters.mapper.ufir_rj;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.UfirRJ;

public class UfirRjMapperEntity {

    public static Index toDomain(UfirRJ entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static UfirRJ toEntity(Index domain){
        return new UfirRJ(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
