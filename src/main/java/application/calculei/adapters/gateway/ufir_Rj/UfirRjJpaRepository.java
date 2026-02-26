package application.calculei.adapters.gateway.ufir_Rj;

import application.calculei.adapters.mapper.ufir_rj.UfirRjMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.UfirRJ;
import application.calculei.infraestructure.repository.ufir_rj.UfirRjIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UfirRjJpaRepository implements IndexRepository {

    private final UfirRjIndexRepository repository;

    public UfirRjJpaRepository(UfirRjIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findByDescricao(String codigo) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<UfirRJ> listEntity = repository.findAll();
        return listEntity.stream().map(UfirRjMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<UfirRJ> listEntity = repository.findByValor(valor);
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
}
