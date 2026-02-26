package application.calculei.adapters.gateway.imab;

import application.calculei.adapters.mapper.imab.ImabMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IMAB;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.imab.ImabIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ImabJpaRepository implements IndexRepository {

    private final ImabIndexRepository repository;

    public ImabJpaRepository(ImabIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findByDescricao(String codigo) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<IMAB> listEntity = repository.findAll();
        return listEntity.stream().map(ImabMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<IMAB> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(ImabMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IMAB> entity = repository.findAll().stream().max(Comparator.comparing(IMAB::getDataInit));
        return entity.map(ImabMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IMAB> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(ImabMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IMAB> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(ImabMapperEntity::toDomain).toList();
    }
}
