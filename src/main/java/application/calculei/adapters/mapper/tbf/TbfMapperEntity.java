package application.calculei.adapters.mapper.tbf;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.TBF;

public class TbfMapperEntity {

    private TbfMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(TBF entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static TBF toEntity(Index domain){
        return new TBF(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
