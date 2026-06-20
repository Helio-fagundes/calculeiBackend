package application.calculei.adapters.gateway.taxa_legal;

import application.calculei.adapters.mapper.taxa_legal.TaxaLegalMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.TaxaLegal;
import application.calculei.infraestructure.repository.taxa_legal.TaxaLegalIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TaxaLegalJpaRepository implements IndexRepository {

    private final TaxaLegalIndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public TaxaLegalJpaRepository(TaxaLegalIndexRepository repository, IndiceBcPort indiceBcPort) {
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    @Override
    public List<Index> findAll() {
        List<TaxaLegal> listEntity = repository.findAll();
        return listEntity.stream().map(TaxaLegalMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<TaxaLegal> entity = repository.findAll().stream().max(Comparator.comparing(TaxaLegal::getDataInit));
        return entity.map(TaxaLegalMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<TaxaLegal> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(TaxaLegalMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<TaxaLegal> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(TaxaLegalMapperEntity::toDomain).toList();
    }

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indiceBcPort.findBySerie("TAXA_LEGAL")
                .orElseThrow(() -> new RuntimeException("Índice Taxa_Legal não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(TaxaLegal::getDataInit)
                .toList();

        List<TaxaLegal> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    TaxaLegal taxaLegal = new TaxaLegal();
                    taxaLegal.setDataInit(index.getDataInit());
                    taxaLegal.setFator(index.getFator());
                    taxaLegal.setIndiceBC(indiceBC);
                    return taxaLegal;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(TaxaLegal::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        TaxaLegal taxaLegal = repository.findByDataInit(dataInit);
        if (taxaLegal == null) {
            throw new DataNotFoundException("Índice Taxa_Legal não encontrado para a data: " + dataInit);
        }
        return TaxaLegalMapperEntity.toDomain(taxaLegal);
    }
}
