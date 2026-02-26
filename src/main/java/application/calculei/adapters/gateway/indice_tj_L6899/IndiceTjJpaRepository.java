package application.calculei.adapters.gateway.indice_tj_L6899;

import application.calculei.adapters.mapper.indice_tj_L6899.IndiceTjL6899MapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Indice_TJ_L6899;
import application.calculei.infraestructure.repository.indice_tj_L6899.TjL6899IndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class IndiceTjJpaRepository implements IndexRepository {

    private final TjL6899IndexRepository repository;

    public IndiceTjJpaRepository(TjL6899IndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findByDescricao(String codigo) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<Indice_TJ_L6899> listEntity = repository.findAll();
        return listEntity.stream().map(IndiceTjL6899MapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<Indice_TJ_L6899> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(IndiceTjL6899MapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<Indice_TJ_L6899> entity = repository.findAll().stream().max(Comparator.comparing(Indice_TJ_L6899::getDataInit));
        return entity.map(IndiceTjL6899MapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<Indice_TJ_L6899> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(IndiceTjL6899MapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<Indice_TJ_L6899> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(IndiceTjL6899MapperEntity::toDomain).toList();
    }
}
