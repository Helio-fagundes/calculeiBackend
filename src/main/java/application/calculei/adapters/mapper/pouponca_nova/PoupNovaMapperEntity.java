package application.calculei.adapters.mapper.pouponca_nova;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.PoupNova;

import java.time.LocalDate;

public class PoupNovaMapperEntity {

    public static Index toDomain(PoupNova entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static PoupNova toEntity(Index domain, LocalDate dataFim){
        return new PoupNova(domain.getId(), domain.getFator(), domain.getDataInit(),  new IndiceBC());
    }
}
