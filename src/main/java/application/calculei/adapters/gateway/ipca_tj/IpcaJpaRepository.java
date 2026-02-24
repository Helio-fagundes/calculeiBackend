package application.calculei.adapters.gateway.ipca_tj;

import application.calculei.adapters.mapper.ipca_tj.IpcaTjMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCA_TJ;
import application.calculei.infraestructure.repository.ipca_tj.IpcaTjIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class IpcaJpaRepository implements IndexRepository {

    private final IpcaTjIndexRepository repository;

    public IpcaJpaRepository(IpcaTjIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Index> findAll() {
        List<IPCA_TJ> listEntity = repository.findAll();
        return listEntity.stream().map(IpcaTjMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<IPCA_TJ> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(IpcaTjMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IPCA_TJ> entity = repository.findAll().stream().max(Comparator.comparing(IPCA_TJ::getDataInit));
        return entity.map(IpcaTjMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IPCA_TJ> entity = repository.findByDataInitBetween(dataInit, dataFim);
        return entity.stream().map(IpcaTjMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IPCA_TJ> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IpcaTjMapperEntity::toDomain).toList();
    }
}
