package application.calculei.adapters.gateway.poupanca_antiga;

import application.calculei.adapters.mapper.poupanca_antiga.PoupAntigaMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.PoupAntiga;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.poupanca_antiga.PoupAntigaIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Transactional
public class PoupAntigaJpaRepository implements IndexRepository {

    private final PoupAntigaIndexRepository repository;
    public final IndicesBcIndexRepository indicesBcIndexRepository;

    public PoupAntigaJpaRepository(PoupAntigaIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    @Override
    public List<Index> findAll() {
        List<PoupAntiga> listEntity = repository.findAll();
        return listEntity.stream().map(PoupAntigaMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<PoupAntiga> entity = repository.findAll().stream().max(Comparator.comparing(PoupAntiga::getDataInit));
        return entity.map(PoupAntigaMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<PoupAntiga> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(PoupAntigaMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<PoupAntiga> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(PoupAntigaMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("POUPANTIGA")
                .orElseThrow(() -> new RuntimeException("Índice Poupança antiga não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(PoupAntiga::getDataInit)
                .toList();

        List<PoupAntiga> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    PoupAntiga poupAntiga = new PoupAntiga();
                    poupAntiga.setDataInit(index.getDataInit());
                    poupAntiga.setFator(index.getFator());
                    poupAntiga.setIndiceBC(indiceBC);
                    return poupAntiga;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(PoupAntiga::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        PoupAntiga poupAntiga = repository.findByDataInit(dataInit);
        if (poupAntiga == null) {
            throw new DataNotFoundException("Índice Poupança antiga não encontrado para a data: " + dataInit);
        }
        return PoupAntigaMapperEntity.toDomain(poupAntiga);
    }
}
