package application.calculei.adapters.gateway.indice_bc;

import application.calculei.adapters.mapper.indice_bc.IndiceBcMapper;
import application.calculei.domain.models.IndiceBcDomain;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
public class IndiceBcJpaRepository implements IndiceBcPort {

    private final IndicesBcIndexRepository repository;

    public IndiceBcJpaRepository(IndicesBcIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBcDomain> findBySerie(String serie) {
        return repository.findBySerie(serie)
                .map(IndiceBcMapper::toDomain);
    }

    @Override
    public void updateLastUpdate(String serie, LocalDate lastUpdate) {
        repository.updateLastUpdate(serie, lastUpdate);
    }

    @Override
    public List<IndiceBcDomain> getAll() {
        return repository.findAll()
                .stream()
                .map(IndiceBcMapper::toDomain)
                .toList();
    }

}
