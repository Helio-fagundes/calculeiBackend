package application.calculei.adapters.gateway.indice_tj_L11960_selic;

import application.calculei.adapters.mapper.indice_tj_L11960_selic.IndiceTjL11960SelicMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Indice_TJ_L11960_Selic;
import application.calculei.infraestructure.repository.indice_tj_L11960_selic.TjL11960SelicIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class IndiceTjJpaRepository implements IndexRepository {

    private final TjL11960SelicIndexRepository repository;

    public IndiceTjJpaRepository(TjL11960SelicIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findByDescricao(String codigo) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<Indice_TJ_L11960_Selic> listEntity = repository.findAll();
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<Indice_TJ_L11960_Selic> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<Indice_TJ_L11960_Selic> entity = repository.findAll().stream().max(Comparator.comparing(Indice_TJ_L11960_Selic::getDataInit));
        return entity.map(IndiceTjL11960SelicMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<Indice_TJ_L11960_Selic> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<Indice_TJ_L11960_Selic> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IndiceTjL11960SelicMapperEntity::toDomain).toList();
    }
}
