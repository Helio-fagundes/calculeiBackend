package application.calculei.adapters.gateway.igpm;

import application.calculei.adapters.mapper.igpm.IgpmMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IGPM;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.igpm.IgpmIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IgpmJpaRepository implements IndexRepository {

    private final IgpmIndexRepository repository;

    public IgpmJpaRepository(IgpmIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<IGPM> listEntity = repository.findAll();
        return listEntity.stream().map(IgpmMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IGPM> entity = repository.findAll().stream().max(Comparator.comparing(IGPM::getDataInit));
        return entity.map(IgpmMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IGPM> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IgpmMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IGPM> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IgpmMapperEntity::toDomain).toList();
    }
}
