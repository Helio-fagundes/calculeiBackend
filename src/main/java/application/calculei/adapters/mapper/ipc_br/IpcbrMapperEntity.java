package application.calculei.adapters.mapper.ipc_br;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCBR;
import application.calculei.infraestructure.entity.IndiceBC;

public class IpcbrMapperEntity {

    public static Index toDomain(IPCBR entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IPCBR toEntity(Index domain){
        return new IPCBR(domain.getId(),  domain.getFator(),domain.getDataInit(), new IndiceBC());
    }
}
