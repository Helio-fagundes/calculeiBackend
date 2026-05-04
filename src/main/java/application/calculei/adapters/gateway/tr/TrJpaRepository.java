package application.calculei.adapters.gateway.tr;

import application.calculei.adapters.mapper.ipca_e.IpcaeMapperEntity;
import application.calculei.adapters.mapper.tr.TrMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.TR;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.tr.TrIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;

import java.util.List;
import java.util.Optional;

public class TrJpaRepository implements IndexRepository {

    private final TrIndexRepository repository;
    private final IndicesBcIndexRepository  indicesBcIndexRepository;

    public TrJpaRepository(
            TrIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository
    ) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public List<Index> findAll() {
        List<TR> listEntity = repository.findAll();
        return listEntity.stream().map(TrMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<TR> entity = repository.findAll().stream().max(Comparator.comparing(TR::getDataInit));
        return entity.map(TrMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<TR> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(TrMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<TR> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(TrMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("TR")
                .orElseThrow(() -> new RuntimeException("Índice TR não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(TR::getDataInit)
                .toList();

        List<TR> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    TR tr = new TR();
                    tr.setDataInit(index.getDataInit());
                    tr.setFator(index.getFator());
                    tr.setIndiceBC(indiceBC);
                    return tr;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(TR::getDataInit)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("Nenhuma data encontrada para o indice TR"));
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        TR tr = repository.findByDataInit(dataInit);
        if (tr == null) {
            throw new DataNotFoundException("Índice TR não encontrado para a data: " + dataInit);
        }
        return TrMapperEntity.toDomain(tr);
    }
}
