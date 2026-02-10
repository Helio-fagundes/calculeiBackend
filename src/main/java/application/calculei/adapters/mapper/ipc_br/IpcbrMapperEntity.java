package application.calculei.adapters.mapper.ipc_br;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCBR;

public class IpcbrMapperEntity {

    public static Index toDomain(IPCBR entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static IPCBR toEntity(Index domain){
        return new IPCBR(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
