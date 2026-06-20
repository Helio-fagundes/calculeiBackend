package application.calculei.adapters.gateway.selic;

import application.calculei.adapters.mapper.selic.SelicDiarioMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.SelicDiario;
import application.calculei.infraestructure.repository.selic.SelicDiarioIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SelicDiarioJpaRepository implements IndexRepository {

    private final SelicDiarioIndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public SelicDiarioJpaRepository(SelicDiarioIndexRepository repository, IndiceBcPort indiceBcPort) {
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    @Override
    public List<Index> findAll() {
        List<SelicDiario> listEntity = repository.findAll();
        return listEntity.stream().map(SelicDiarioMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<SelicDiario> entity = repository.findAll().stream().max(Comparator.comparing(SelicDiario::getDataInit));
        return entity.map(SelicDiarioMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<SelicDiario> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(SelicDiarioMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<SelicDiario> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(SelicDiarioMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indiceBcPort.findBySerie("SELIC_DIARIO")
                .orElseThrow(() -> new RuntimeException("Índice Selic diario não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(SelicDiario::getDataInit)
                .toList();

        List<SelicDiario> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    SelicDiario selic = new SelicDiario();
                    selic.setDataInit(index.getDataInit());
                    selic.setFator(index.getFator());
                    selic.setIndiceBC(indiceBC);
                    return selic;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(SelicDiario::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        SelicDiario selic = repository.findByDataInit(dataInit);
        if (selic == null) {
            throw new DataNotFoundException("Índice Selic diario não encontrado para a data: " + dataInit);
        }
        return SelicDiarioMapperEntity.toDomain(selic);
    }
}
