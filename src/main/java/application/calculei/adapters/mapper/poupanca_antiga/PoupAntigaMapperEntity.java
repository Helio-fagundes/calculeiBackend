package application.calculei.adapters.mapper.poupanca_antiga;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.PoupAntiga;

public class PoupAntigaMapperEntity {

    public static Index toDomain(PoupAntiga entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static PoupAntiga toEntity(Index domain){
        return new PoupAntiga(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit(), new IndiceBC());
    }
}
