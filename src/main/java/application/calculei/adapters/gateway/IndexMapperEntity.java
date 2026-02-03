package application.calculei.adapters.gateway;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.BaseEntity;

public class IndexMapperEntity {

    public static Index toDomain(BaseEntity baseEntity){
        return new Index(baseEntity.getId(), baseEntity.getNome(), baseEntity.getFator(), baseEntity.getValor(), baseEntity.getDataInit());
    }

}
