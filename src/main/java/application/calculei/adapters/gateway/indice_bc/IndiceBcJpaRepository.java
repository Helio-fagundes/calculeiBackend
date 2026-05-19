package application.calculei.adapters.gateway.indice_bc;

import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;

import java.util.Optional;

public class IndiceBcJpaRepository implements IndiceBcPort {

    private final IndicesBcIndexRepository repository;

    public IndiceBcJpaRepository(IndicesBcIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return repository.findBySerie(serie);
    }

}
