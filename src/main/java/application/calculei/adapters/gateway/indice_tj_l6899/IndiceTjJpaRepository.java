package application.calculei.adapters.gateway.indice_tj_l6899;

import application.calculei.adapters.mapper.ufir_rj.UfirRjMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.UfirRJ;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ufir_rj.UfirRjIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Transactional
public class IndiceTjJpaRepository implements IndexRepository {

    private final UfirRjIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public IndiceTjJpaRepository(UfirRjIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
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

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("TJ6899")
                .orElseThrow(() -> new RuntimeException("Índice Tj6899 não encontrado na base de dados."));

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
                    UfirRJ tj6899 = new UfirRJ();
                    tj6899.setDataInit(index.getDataInit());
                    tj6899.setFator(index.getFator());
                    tj6899.setIndiceBC(indiceBC);
                    return tj6899;
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
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        UfirRJ entity = repository.findByDataInit(dataInit);
        if (entity == null) {
            throw new DataNotFoundException("Índice Tj6899 não encontrado para a data: " + dataInit);
        }
        return UfirRjMapperEntity.toDomain(entity);
    }
}
