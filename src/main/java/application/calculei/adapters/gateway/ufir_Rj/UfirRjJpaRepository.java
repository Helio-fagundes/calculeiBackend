package application.calculei.adapters.gateway.ufir_Rj;

import application.calculei.adapters.mapper.ipca_e.IpcaeMapperEntity;
import application.calculei.adapters.mapper.ufir_rj.UfirRjMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.UfirRJ;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ufir_rj.UfirRjIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UfirRjJpaRepository implements IndexRepository {

    private final UfirRjIndexRepository repository;
    private final IndicesBcIndexRepository  indicesBcIndexRepository;

    public UfirRjJpaRepository(
            UfirRjIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository
    ) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public List<Index> findAll() {
        List<UfirRJ> listEntity = repository.findAll();
        return listEntity.stream().map(UfirRjMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<UfirRJ> entity = repository.findAll().stream().max(Comparator.comparing(UfirRJ::getDataInit));
        return entity.map(UfirRjMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<UfirRJ> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(UfirRjMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<UfirRJ> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(UfirRjMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("UFIR")
                .orElseThrow(() -> new RuntimeException("Índice UFIR não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(UfirRJ::getDataInit)
                .toList();

        List<UfirRJ> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    UfirRJ ufirRJ = new UfirRJ();
                    ufirRJ.setDataInit(index.getDataInit());
                    ufirRJ.setFator(index.getFator());
                    ufirRJ.setIndiceBC(indiceBC);
                    return ufirRJ;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(UfirRJ::getDataInit)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("Nenhuma data encontrada para o indice Ufir"));
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        UfirRJ ufirRJ = repository.findByDataInit(dataInit);
        if (ufirRJ == null) {
            throw new DataNotFoundException("Índice Ufir não encontrado para a data: " + dataInit);
        }
        return UfirRjMapperEntity.toDomain(ufirRJ);
    }
}
