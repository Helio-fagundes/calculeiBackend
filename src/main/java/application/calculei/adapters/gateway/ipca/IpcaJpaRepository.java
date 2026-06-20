package application.calculei.adapters.gateway.ipca;

import application.calculei.adapters.mapper.ipca.IpcaMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IPCA;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.ipca.IpcaIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IpcaJpaRepository implements IndexRepository {

    private final IpcaIndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public IpcaJpaRepository(IpcaIndexRepository repository, IndiceBcPort indiceBcPort) {
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
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

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indiceBcPort.findBySerie("IPCA")
                .orElseThrow(() -> new RuntimeException("Índice IPCA não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(IPCA::getDataInit)
                .toList();

        List<IPCA> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    IPCA ipca = new IPCA();
                    ipca.setDataInit(index.getDataInit());
                    ipca.setFator(index.getFator());
                    ipca.setIndiceBC(indiceBC);
                    return ipca;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(IPCA::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        IPCA ipca = repository.findByDataInit(dataInit);
        if (ipca == null) {
            throw new DataNotFoundException("Índice IPCA não encontrado para a data: " + dataInit);
        }
        return IpcaMapperEntity.toDomain(ipca);
    }
}
