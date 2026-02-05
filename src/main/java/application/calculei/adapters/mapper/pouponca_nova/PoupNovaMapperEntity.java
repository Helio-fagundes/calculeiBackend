package application.calculei.adapters.mapper.pouponca_nova;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.PoupNova;

public class PoupNovaMapperEntity {

    public static Index toDomain(PoupNova entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static PoupNova toEntity(Index domain){
        return new PoupNova(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit());
    }
}
