package application.calculei.adapters.gateway.ipca_tl;

import application.calculei.adapters.mapper.ipca_tl.IpcaTlMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCA_Tl;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.ipca_tl.IpcaTlIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IpcatlJpaRepository implements IndexRepository {

    private final IpcaTlIndexRepository repository;

    public IpcatlJpaRepository(IpcaTlIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<IPCA_Tl> listEntity = repository.findAll();
        return listEntity.stream().map(IpcaTlMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IPCA_Tl> entity = repository.findAll().stream().max(Comparator.comparing(IPCA_Tl::getDataInit));
        return entity.map(IpcaTlMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IPCA_Tl> entity = repository.findByDataInitBetween(dataInit, dataFim);
        return entity.stream().map(IpcaTlMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IPCA_Tl> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IpcaTlMapperEntity::toDomain).toList();
    }
}
