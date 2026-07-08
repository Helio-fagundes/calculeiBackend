package application.calculei.adapters.gateway.selic;

import application.calculei.adapters.mapper.selic.SelicMensalMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.SelicMensal;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Transactional
public class SelicMensalJpaRepository implements IndexRepository {

    private final SelicMensalIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public SelicMensalJpaRepository(SelicMensalIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public List<Index> findAll() {
        List<SelicMensal> listEntity = repository.findAll();
        return listEntity.stream().map(SelicMensalMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<SelicMensal> entity = repository.findAll().stream().max(Comparator.comparing(SelicMensal::getDataInit));
        return entity.map(SelicMensalMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<SelicMensal> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(SelicMensalMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<SelicMensal> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(SelicMensalMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("SELIC_MENSAL")
                .orElseThrow(() -> new RuntimeException("Índice Selic não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(SelicMensal::getDataInit)
                .toList();

        List<SelicMensal> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    SelicMensal selic = new SelicMensal();
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
                .map(SelicMensal::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        SelicMensal selic = repository.findByDataInit(dataInit);
        if (selic == null) {
            throw new DataNotFoundException("Índice Selic não encontrado para a data: " + dataInit);
        }
        return SelicMensalMapperEntity.toDomain(selic);
    }
}
