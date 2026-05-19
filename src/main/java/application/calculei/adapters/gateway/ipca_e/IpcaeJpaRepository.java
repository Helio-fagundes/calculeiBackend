package application.calculei.adapters.gateway.ipca_e;

import application.calculei.adapters.mapper.ipca_e.IpcaeMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca_e.IpcaeIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IpcaeJpaRepository implements IndexRepository {

    private final IpcaeIndexRepository repository;
    private final IndiceBcPort indiceBcPort;

     public IpcaeJpaRepository(IpcaeIndexRepository repository, IndiceBcPort indiceBcPort) {
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    @Override
    public List<Index> findAll() {
        List<IPCAE> listEntity = repository.findAll();
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

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indiceBcPort.findBySerie("IPCAE")
                .orElseThrow(() -> new RuntimeException("Índice IPCA_E não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(IPCAE::getDataInit)
                .toList();

        List<IPCAE> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    IPCAE ipcae = new IPCAE();
                    ipcae.setDataInit(index.getDataInit());
                    ipcae.setFator(index.getFator());
                    ipcae.setIndiceBC(indiceBC);
                    return ipcae;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(IPCAE::getDataInit)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("Nenhuma data encontrada para o indice IPCA_E"));
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        IPCAE ipcae = repository.findByDataInit(dataInit);
        if (ipcae == null) {
            throw new DataNotFoundException("Índice IPCA_E não encontrado para a data: " + dataInit);
        }
        return IpcaeMapperEntity.toDomain(ipcae);
    }
}
