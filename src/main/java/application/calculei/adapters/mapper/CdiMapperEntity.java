package application.calculei.adapters.mapper;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.BaseEntity;
import application.calculei.infraestructure.entity.CDI;
import application.calculei.infraestructure.entity.IndiceBC;

public class CdiMapperEntity {

    public static Index toDomain(CDI cdi){
        return new Index(cdi.getId(), cdi.getNome(), cdi.getFator(), cdi.getValor(), cdi.getDataInit());
    }

    public static CDI toEntity(Index index, IndiceBC indiceBC){
        return new CDI(index.getId(), index.getNome(), index.getFator(), index.getValor(), index.getDataInit(), indiceBC);
    }
}
