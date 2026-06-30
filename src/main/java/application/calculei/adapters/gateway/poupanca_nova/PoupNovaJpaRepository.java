package application.calculei.adapters.gateway.poupanca_nova;

import application.calculei.adapters.mapper.pouponca_nova.PoupNovaMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.PoupNova;
import application.calculei.infraestructure.repository.poupanca_nova.PoupNovaIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Transactional
public class PoupNovaJpaRepository implements IndexRepository {

    private final PoupNovaIndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public PoupNovaJpaRepository(PoupNovaIndexRepository repository, IndiceBcPort indiceBcPort) {
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    @Override
    public List<Index> findAll() {
        List<PoupNova> listEntity = repository.findAll();
        return listEntity.stream().map(PoupNovaMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<PoupNova> entity = repository.findAll().stream().max(Comparator.comparing(PoupNova::getDataInit));
        return entity.map(PoupNovaMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<PoupNova> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(PoupNovaMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<PoupNova> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(PoupNovaMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indiceBcPort.findBySerie("POUPNOVA")
                .orElseThrow(() -> new RuntimeException("Índice Poupança nova não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(PoupNova::getDataInit)
                .toList();

        List<PoupNova> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    PoupNova poupNova = new PoupNova();
                    poupNova.setDataInit(index.getDataInit());
                    poupNova.setFator(index.getFator());
                    poupNova.setIndiceBC(indiceBC);
                    return poupNova;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(PoupNova::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        PoupNova poupNova = repository.findByDataInit(dataInit);
        if (poupNova == null) {
            throw new DataNotFoundException("Índice Poupança Nova não encontrado para a data: " + dataInit);
        }
        return PoupNovaMapperEntity.toDomain(poupNova);
    }
}
