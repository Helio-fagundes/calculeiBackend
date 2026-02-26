package application.calculei.adapters.gateway.ipc_br;

import application.calculei.adapters.mapper.ipc_br.IpcbrMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCBR;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.ipc_br.IpcbrIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class IpcBrJpaRepository implements IndexRepository {

    private final IpcbrIndexRepository repository;

    public IpcBrJpaRepository(IpcbrIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findByDescricao(String codigo) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<IPCBR> listEntity = repository.findAll();
        return listEntity.stream().map(IpcbrMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<IPCBR> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(IpcbrMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IPCBR> entity = repository.findAll().stream().max(Comparator.comparing(IPCBR::getDataInit));
        return entity.map(IpcbrMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IPCBR> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IpcbrMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IPCBR> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IpcbrMapperEntity::toDomain).toList();
    }
}
