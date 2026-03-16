package application.calculei.adapters.gateway.ipca;

import application.calculei.adapters.mapper.ipca.IpcaMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCA;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.ipca.IpcaIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IpcaJpaRepository implements IndexRepository {

    private final IpcaIndexRepository repository;

    public IpcaJpaRepository(IpcaIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<IPCA> listEntity = repository.findAll();
        return listEntity.stream().map(IpcaMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IPCA> entity = repository.findAll().stream().max(Comparator.comparing(IPCA::getDataInit));
        return entity.map(IpcaMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IPCA> entity = repository.findByDataInitBetween(dataInit, dataFim);
        return entity.stream().map(IpcaMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IPCA> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IpcaMapperEntity::toDomain).toList();
    }
}
