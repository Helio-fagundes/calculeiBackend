package application.calculei.adapters.gateway.taxa_legal;

import application.calculei.adapters.mapper.taxa_legal.TaxaLegalMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.TaxaLegal;
import application.calculei.infraestructure.repository.taxa_legal.TaxaLegalIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TaxaLegalJpaRepository implements IndexRepository {

    private final TaxaLegalIndexRepository repository;

    public TaxaLegalJpaRepository(TaxaLegalIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
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
}
