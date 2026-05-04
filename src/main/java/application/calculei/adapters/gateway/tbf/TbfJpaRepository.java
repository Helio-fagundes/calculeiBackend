package application.calculei.adapters.gateway.tbf;

import application.calculei.adapters.mapper.ipca_e.IpcaeMapperEntity;
import application.calculei.adapters.mapper.tbf.TbfMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.TBF;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.tbf.TbfIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TbfJpaRepository implements IndexRepository {

    private final TbfIndexRepository repository;
    private final IndicesBcIndexRepository  indicesBcIndexRepository;

    public TbfJpaRepository(
            TbfIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository
    ) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public List<Index> findAll() {
        List<TBF> listEntity = repository.findAll();
        return listEntity.stream().map(TbfMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<TBF> entity = repository.findAll().stream().max(Comparator.comparing(TBF::getDataInit));
        return entity.map(TbfMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<TBF> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(TbfMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<TBF> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(TbfMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("TBF")
                .orElseThrow(() -> new RuntimeException("Índice TBF não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(TBF::getDataInit)
                .toList();

        List<TBF> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    TBF tbf = new TBF();
                    tbf.setDataInit(index.getDataInit());
                    tbf.setFator(index.getFator());
                    tbf.setIndiceBC(indiceBC);
                    return tbf;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(TBF::getDataInit)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("Nenhuma data encontrada para o indice TBF"));
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        TBF tbf = repository.findByDataInit(dataInit);
        if (tbf == null) {
            throw new DataNotFoundException("Índice TBF não encontrado para a data: " + dataInit);
        }
        return TbfMapperEntity.toDomain(tbf);
    }
}
