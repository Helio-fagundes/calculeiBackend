package application.calculei.adapters.gateway.indice_tj_L11960_selic;

import application.calculei.adapters.mapper.indice_tj_L11960_selic.IndiceTjL11960SelicMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Indice_TJ_L11960_Selic;
import application.calculei.infraestructure.repository.indice_tj_L11960_selic.TjL11960SelicIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Transactional
public class IndiceTjJpaRepository implements IndexRepository {

    private final TjL11960SelicIndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public IndiceTjJpaRepository(TjL11960SelicIndexRepository repository, IndiceBcPort indiceBcPort) {
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    @Override
    public List<Index> findAll() {
        List<Indice_TJ_L11960_Selic> listEntity = repository.findAll();
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<Indice_TJ_L11960_Selic> entity = repository.findAll().stream().max(Comparator.comparing(Indice_TJ_L11960_Selic::getDataInit));
        return entity.map(IndiceTjL11960SelicMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<Indice_TJ_L11960_Selic> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<Indice_TJ_L11960_Selic> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indiceBcPort.findBySerie("TJ11960")
                .orElseThrow(() -> new RuntimeException("Índice Tj11960 não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(Indice_TJ_L11960_Selic::getDataInit)
                .toList();

        List<Indice_TJ_L11960_Selic> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    Indice_TJ_L11960_Selic tjL11960Selic = new Indice_TJ_L11960_Selic();
                    tjL11960Selic.setDataInit(index.getDataInit());
                    tjL11960Selic.setFator(index.getFator());
                    tjL11960Selic.setIndiceBC(indiceBC);
                    return tjL11960Selic;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(Indice_TJ_L11960_Selic::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        Indice_TJ_L11960_Selic tjL11960Selic = repository.findByDataInit(dataInit);
        if (tjL11960Selic == null) {
            throw new DataNotFoundException("Índice Tj11960 não encontrado para a data: " + dataInit);
        }
        return IndiceTjL11960SelicMapperEntity.toDomain(tjL11960Selic);
    }
}
