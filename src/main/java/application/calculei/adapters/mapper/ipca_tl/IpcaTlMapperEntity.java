package application.calculei.adapters.mapper.ipca_tl;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IPCA_Tl;
import application.calculei.infraestructure.entity.IndiceBC;

public class IpcaTlMapperEntity {

    private IpcaTlMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(IPCA_Tl entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static IPCA_Tl toEntity(Index domain){
        return new IPCA_Tl(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
