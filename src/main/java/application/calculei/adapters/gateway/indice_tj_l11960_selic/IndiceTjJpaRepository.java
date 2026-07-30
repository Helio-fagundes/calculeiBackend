package application.calculei.adapters.gateway.indice_tj_l11960_selic;

import application.calculei.adapters.mapper.indice_tj_l11960_selic.IndiceTjL11960SelicMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.IndiceTjl11960Selic;
import application.calculei.infraestructure.repository.indice_tj_l11960_selic.TjL11960SelicIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Transactional
public class IndiceTjJpaRepository implements IndexRepository {

    private final TjL11960SelicIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public IndiceTjJpaRepository(TjL11960SelicIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public List<Index> findAll() {
        List<IndiceTjl11960Selic> listEntity = repository.findAll();
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IndiceTjl11960Selic> entity = repository.findAll().stream().max(Comparator.comparing(IndiceTjl11960Selic::getDataInit));
        return entity.map(IndiceTjL11960SelicMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IndiceTjl11960Selic> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IndiceTjl11960Selic> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("TJ11960")
                .orElseThrow(() -> new RuntimeException("Índice Tj11960 não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(IndiceTjl11960Selic::getDataInit)
                .toList();

        List<IndiceTjl11960Selic> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    IndiceTjl11960Selic tjL11960Selic = new IndiceTjl11960Selic();
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
                .map(IndiceTjl11960Selic::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        IndiceTjl11960Selic tjL11960Selic = repository.findByDataInit(dataInit);
        if (tjL11960Selic == null) {
            throw new DataNotFoundException("Índice Tj11960 não encontrado para a data: " + dataInit);
        }
        return IndiceTjL11960SelicMapperEntity.toDomain(tjL11960Selic);
    }
}
