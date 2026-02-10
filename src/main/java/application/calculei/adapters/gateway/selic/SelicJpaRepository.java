package application.calculei.adapters.gateway.selic;

import application.calculei.adapters.mapper.selic.SelicMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.Selic;
import application.calculei.infraestructure.repository.selic.SelicIndexRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SelicJpaRepository implements IndexRepository {

    private final SelicIndexRepository repository;

    public SelicJpaRepository(SelicIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Index> findAll() {
        List<Selic> listEntity = repository.findAll();
        return listEntity.stream().map(SelicMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<Selic> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(SelicMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<Selic> entity = repository.findAll().stream().max(Comparator.comparing(Selic::getDataInit));
        return entity.map(SelicMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(Date dataInit, Date dataFim) {
        List<Selic> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(SelicMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(Date dataInit) {
        List<Selic> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(SelicMapperEntity::toDomain).toList();
    }
}
