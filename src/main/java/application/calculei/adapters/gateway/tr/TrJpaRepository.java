package application.calculei.adapters.gateway.tr;

import application.calculei.adapters.mapper.tr.TrMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.TR;
import application.calculei.infraestructure.repository.tr.TrIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrJpaRepository implements IndexRepository {

    private final TrIndexRepository repository;

    public TrJpaRepository(TrIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Index> findAll() {
        List<TR> listEntity = repository.findAll();
        return listEntity.stream().map(TrMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<TR> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(TrMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<TR> entity = repository.findAll().stream().max(Comparator.comparing(TR::getDataInit));
        return entity.map(TrMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<TR> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(TrMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<TR> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(TrMapperEntity::toDomain).toList();
    }
}
