package application.calculei.adapters.gateway.igpdi;

import application.calculei.adapters.mapper.igpdi.IgpdiMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IGPDI;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IgpdiJpaRepository implements IndexRepository {

    private final IgpdiIndexRepository repository;
    private final IndicesBcIndexRepository  indicesBcIndexRepository;

    public IgpdiJpaRepository(
            IgpdiIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public List<Index> findAll() {
        List<IGPDI> listEntity = repository.findAll();
        return listEntity.stream().map(IgpdiMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IGPDI> entity = repository.findAll().stream().max(Comparator.comparing(IGPDI::getDataInit));
        return entity.map(IgpdiMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IGPDI> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IgpdiMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate data) {
        List<IGPDI> listEntity = repository.findByDataInitLessThanEqual(data);
        return listEntity.stream().map(IgpdiMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indice = indicesBcIndexRepository.findBySerie("IGPDI")
                .orElseThrow(() -> new RuntimeException("Indice IGPDI não encontrado na base de dados"));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(IGPDI::getDataInit)
                .toList();

        List<IGPDI> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    IGPDI igpdi = new IGPDI();
                    igpdi.setDataInit(index.getDataInit());
                    igpdi.setFator(index.getFator());
                    igpdi.setIndiceBC(indice);
                    return igpdi;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }

    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(IGPDI::getDataInit)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar a data máxima de início para o índice IGPDI."));
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        IGPDI entity = repository.findByDataInit(dataInit);
        if (entity == null) {
            throw new DataNotFoundException("Índice IGPDI não encontrado para a data: " + dataInit);
        }
        return IgpdiMapperEntity.toDomain(entity);
    }
}
