package application.calculei.adapters.gateway.poupanca_antiga;

import application.calculei.adapters.mapper.poupanca_antiga.PoupAntigaMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.PoupAntiga;
import application.calculei.infraestructure.repository.poupanca_antiga.PoupAntigaIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PoupJpaRepository implements IndexRepository {

    private final PoupAntigaIndexRepository repository;

    public PoupJpaRepository(PoupAntigaIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Index> findAll() {
        List<PoupAntiga> listEntity = repository.findAll();
        return listEntity.stream().map(PoupAntigaMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<PoupAntiga> listEntity = repository.findByValor(valor);
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
}
