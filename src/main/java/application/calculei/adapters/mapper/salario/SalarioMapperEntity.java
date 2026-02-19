package application.calculei.adapters.mapper.salario;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Salario;

public class SalarioMapperEntity {

    public static Index toDomain(Salario entity){
        return new Index(entity.getId(), entity.getNome(), entity.getFator(), entity.getValor(), entity.getDataInit());
    }

    public static Salario toEntity(Index domain){
        return new Salario(domain.getId(), domain.getNome(), domain.getFator(), domain.getValor(), domain.getDataInit(), new IndiceBC());
    }
}
