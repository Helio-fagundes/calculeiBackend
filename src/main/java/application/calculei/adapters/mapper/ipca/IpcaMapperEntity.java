package application.calculei.adapters.mapper.ipca;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCA;
import application.calculei.infraestructure.entity.IndiceBC;

public class IpcaMapperEntity {

    private IpcaMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(IPCA entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IPCA toEntity(Index domain){
        return new IPCA(domain.getId(),  domain.getFator(),domain.getDataInit(), new IndiceBC());
    }
}
