package application.calculei.adapters.gateway.ipca_tl;

import application.calculei.adapters.mapper.ipca_e.IpcaeMapperEntity;
import application.calculei.adapters.mapper.ipca_tl.IpcaTlMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.entity.IPCA_Tl;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca_tl.IpcaTlIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IpcatlJpaRepository implements IndexRepository {

    private final IpcaTlIndexRepository repository;
    private final IndicesBcIndexRepository  indicesBcIndexRepository;

    public IpcatlJpaRepository(
            IpcaTlIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository
    ) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
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

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("IPCA_TL")
                .orElseThrow(() -> new RuntimeException("Índice IPCA_TL não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(IPCA_Tl::getDataInit)
                .toList();

        List<IPCA_Tl> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    IPCA_Tl ipcaTl = new IPCA_Tl();
                    ipcaTl.setDataInit(index.getDataInit());
                    ipcaTl.setFator(index.getFator());
                    ipcaTl.setIndiceBC(indiceBC);
                    return ipcaTl;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(IPCA_Tl::getDataInit)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("Nenhuma data encontrada para o indice IPCA_TL"));
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        IPCA_Tl ipcaTl = repository.findByDataInit(dataInit);
        if (ipcaTl == null) {
            throw new DataNotFoundException("Índice IPCA_TL não encontrado para a data: " + dataInit);
        }
        return IpcaTlMapperEntity.toDomain(ipcaTl);
    }
}
