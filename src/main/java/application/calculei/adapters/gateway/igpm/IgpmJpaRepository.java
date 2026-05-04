package application.calculei.adapters.gateway.igpm;

import application.calculei.adapters.mapper.igpm.IgpmMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IGPDI;
import application.calculei.infraestructure.entity.IGPM;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.igpm.IgpmIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class IgpmJpaRepository implements IndexRepository {

    private final IgpmIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public IgpmJpaRepository(
            IgpmIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public List<Index> findAll() {
        List<IGPM> listEntity = repository.findAll();
        return listEntity.stream().map(IgpmMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IGPM> entity = repository.findAll().stream().max(Comparator.comparing(IGPM::getDataInit));
        return entity.map(IgpmMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IGPM> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IgpmMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IGPM> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IgpmMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("IGPM")
                .orElseThrow(() -> new RuntimeException("Índice IGPM não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(IGPM::getDataInit)
                .toList();

        List<IGPM> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    IGPM igpm = new IGPM();
                    igpm.setDataInit(index.getDataInit());
                    igpm.setFator(index.getFator());
                    igpm.setIndiceBC(indiceBC);
                    return igpm;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }

    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(IGPM::getDataInit)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar a data máxima de atualização do índice IGPM."));
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        IGPM igpm = repository.findByDataInit(dataInit);
        if (igpm == null) {
            throw new DataNotFoundException("Índice IGPM não encontrado para a data: " + dataInit);
        }
        return IgpmMapperEntity.toDomain(igpm);
    }
}
