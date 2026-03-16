package application.calculei.adapters.mapper.ipca_e;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.entity.IndiceBC;

public class IpcaeMapperEntity {

    public static Index toDomain(IPCAE entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IPCAE toEntity(Index domain){
        return new IPCAE(domain.getId(),  domain.getFator(),domain.getDataInit(), new IndiceBC());
    }
}
