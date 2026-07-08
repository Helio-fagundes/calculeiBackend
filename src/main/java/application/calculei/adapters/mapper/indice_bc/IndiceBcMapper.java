package application.calculei.adapters.mapper.indice_bc;

import application.calculei.domain.models.IndiceBcDomain;
import application.calculei.infraestructure.entity.IndiceBC;

public class IndiceBcMapper {

    public static IndiceBcDomain toDomain(IndiceBC entity) {
        if (entity == null) {
            return null;
        }

        return new IndiceBcDomain(
                entity.getID_BC(),
                entity.getSerie(),
                entity.getLastUpdate()
        );
    }

    public static IndiceBC toEntity(IndiceBcDomain domain) {
        if (domain == null) {
            return null;
        }

        IndiceBC entity = new IndiceBC();
        entity.setID_BC(domain.getId());
        entity.setSerie(domain.getSerie());
        entity.setLastUpdate(domain.getLastUpdate());
        return entity;
    }
}
