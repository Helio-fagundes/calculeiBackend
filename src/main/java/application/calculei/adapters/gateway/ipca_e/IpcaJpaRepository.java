package application.calculei.adapters.gateway.ipca_e;

import application.calculei.adapters.mapper.ipca_e.IpcaeMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.ipca_e.IpcaeIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class IpcaJpaRepository implements IndexRepository {

    private final IpcaeIndexRepository repository;

    public IpcaJpaRepository(IpcaeIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findByDescricao(String codigo) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<IPCAE> listEntity = repository.findAll();
        return listEntity.stream().map(IpcaeMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<IPCAE> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(IpcaeMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IPCAE> entity = repository.findAll().stream().max(Comparator.comparing(IPCAE::getDataInit));
        return entity.map(IpcaeMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IPCAE> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IpcaeMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IPCAE> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IpcaeMapperEntity::toDomain).toList();
    }
}
