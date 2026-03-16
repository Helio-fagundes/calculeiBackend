package application.calculei.adapters.gateway.tbf;

import application.calculei.adapters.mapper.tbf.TbfMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.TBF;
import application.calculei.infraestructure.repository.tbf.TbfIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TbfJpaRepository implements IndexRepository {

    private final TbfIndexRepository repository;

    public TbfJpaRepository(TbfIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<TBF> listEntity = repository.findAll();
        return listEntity.stream().map(TbfMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<TBF> entity = repository.findAll().stream().max(Comparator.comparing(TBF::getDataInit));
        return entity.map(TbfMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<TBF> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(TbfMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<TBF> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(TbfMapperEntity::toDomain).toList();
    }
}
