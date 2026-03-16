package application.calculei.adapters.gateway.selic;

import application.calculei.adapters.mapper.selic.SelicMensalMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.SelicMensal;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SelicMensalJpaRepository implements IndexRepository {

    private final SelicMensalIndexRepository repository;

    public SelicMensalJpaRepository(SelicMensalIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<SelicMensal> listEntity = repository.findAll();
        return listEntity.stream().map(SelicMensalMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<SelicMensal> entity = repository.findAll().stream().max(Comparator.comparing(SelicMensal::getDataInit));
        return entity.map(SelicMensalMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<SelicMensal> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(SelicMensalMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<SelicMensal> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(SelicMensalMapperEntity::toDomain).toList();
    }
}
