package application.calculei.adapters.mapper.salario;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Salario;

public class SalarioMapperEntity {

    private SalarioMapperEntity() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Index toDomain(Salario entity){
        return new Index(entity.getId(), entity.getFator(), entity.getDataInit());
    }

    public static Salario toEntity(Index domain){
        return new Salario(domain.getId(), domain.getFator(), domain.getDataInit(), new IndiceBC());
    }
}
