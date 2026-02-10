package application.calculei.adapters.mapper.ipca_e;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCAE;

public class IpcaeMapperEntity {

    public static Index toDomain(IPCAE entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static IPCAE toEntity(Index domain){
        return new IPCAE(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
