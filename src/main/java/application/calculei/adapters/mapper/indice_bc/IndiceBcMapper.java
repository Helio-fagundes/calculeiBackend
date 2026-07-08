package application.calculei.adapters.mapper.indice_bc;

import application.calculei.domain.models.IndiceBcDomain;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.usecase.indice_bc.dto.IndicesBcResponseDto;

public class IndiceBcMapper {

    public static IndiceBcDomain toDomain(IndiceBC entity) {
        if (entity == null) {
            return null;
        }

        return new IndiceBcDomain(
                entity.getID_BC(),
                entity.getSerie(),
                entity.getLastUpdate(),
                entity.getDescricao(),
                entity.getCodigo(),
                entity.getPeriodicidade(),
                entity.getUrlBC()
        );
    }

    public static IndicesBcResponseDto toResponseDto(IndiceBcDomain domain) {
        if (domain == null) {
            return null;
        }

        return new IndicesBcResponseDto(
                domain.getCodigo(),
                domain.getDescription(),
                domain.getPeriodicidade(),
                domain.getUrlBC(),
                domain.getLastUpdate()
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
        entity.setDescricao(domain.getDescription());
        entity.setCodigo(domain.getCodigo());
        entity.setPeriodicidade(domain.getPeriodicidade());
        entity.setUrlBC(domain.getUrlBC());
        return entity;
    }
}
