package application.calculei.adapters.gateway.cdi;

import application.calculei.adapters.mapper.cdi.CdiMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.CDI;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CdiJpaRepository implements IndexRepository {

    private final CdiIndexRepository repository;
    private final IndicesBcIndexRepository  indicesBcIndexRepository;

    public CdiJpaRepository(
            CdiIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<CDI> entities = repository.findAll();
        return entities.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<CDI> entity = repository.findAll().stream().max(Comparator.comparing(CDI::getDataInit));
        return entity.map(CdiMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<CDI> entityList = repository.findByDataInitBetween(dataInit, dataFim);
        return entityList.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<CDI> entityList = repository.findByDataInitLessThanEqual(dataInit);
        return entityList.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("CDI")
                .orElseThrow(() -> new RuntimeException("Índice CDI não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(CDI::getDataInit)
                .toList();

        List<CDI> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    CDI cdi = new CDI();
                    cdi.setDataInit(index.getDataInit());
                    cdi.setFator(index.getFator());
                    cdi.setIndiceBC(indiceBC);
                    return cdi;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return null;
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        return null;
    }
}
