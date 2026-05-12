package application.calculei.adapters.gateway.indice_bc;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class IndiceBcJpaRepository implements IndexRepository {

    private final IndicesBcIndexRepository repository;

    public IndiceBcJpaRepository(IndicesBcIndexRepository repository) {
        this.repository = repository;
    }

    public Optional<IndiceBC> findBySerie(String serie) {
        return repository.findBySerie(serie);
    }

    @Override
    public List<Index> findAll() {
        return List.of();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        return Optional.empty();
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        return List.of();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        return List.of();
    }

    @Override
    public void saveAll(List<Index> listEntity) {
    }

    @Override
    public LocalDate findMaxDataInit() {
        return null;
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        return null;
    }
}
