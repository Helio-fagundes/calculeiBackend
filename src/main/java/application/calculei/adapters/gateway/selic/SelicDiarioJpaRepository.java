package application.calculei.adapters.gateway.selic;

import application.calculei.adapters.mapper.selic.SelicDiarioMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.SelicDiario;
import application.calculei.infraestructure.repository.selic.SelicDiarioIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SelicDiarioJpaRepository implements IndexRepository {

    private final SelicDiarioIndexRepository repository;

    public SelicDiarioJpaRepository(SelicDiarioIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
    List<SelicDiario> listEntity = repository.findAll();
    return listEntity.stream().map(SelicDiarioMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<SelicDiario> entity = repository.findAll().stream().max(Comparator.comparing(SelicDiario::getDataInit));
        return entity.map(SelicDiarioMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<SelicDiario> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(SelicDiarioMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<SelicDiario> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(SelicDiarioMapperEntity::toDomain).toList();
    }
}
