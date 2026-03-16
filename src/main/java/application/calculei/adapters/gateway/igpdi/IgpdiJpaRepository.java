package application.calculei.adapters.gateway.igpdi;

import application.calculei.adapters.mapper.igpdi.IgpdiMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IGPDI;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class IgpdiJpaRepository implements IndexRepository {

    private final IgpdiIndexRepository repository;

    public IgpdiJpaRepository(IgpdiIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<IGPDI> listEntity = repository.findAll();
        return listEntity.stream().map(IgpdiMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IGPDI> entity = repository.findAll().stream().max(Comparator.comparing(IGPDI::getDataInit));
        return entity.map(IgpdiMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IGPDI> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IgpdiMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate data) {
        List<IGPDI> listEntity = repository.findByDataInitLessThanEqual(data);
        return listEntity.stream().map(IgpdiMapperEntity::toDomain).toList();
    }
}
