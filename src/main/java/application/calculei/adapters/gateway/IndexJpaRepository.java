package application.calculei.adapters.gateway;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.BaseEntity;
import application.calculei.infraestructure.repository.JpaIndexRepository;

import java.util.Date;
import java.util.List;

public class IndexJpaRepository implements IndexRepository {

    private final JpaIndexRepository jpaRepository;

    public IndexJpaRepository(JpaIndexRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Index> findAll() {
        List<BaseEntity> entities = jpaRepository.findAll();
        return entities.stream().map(IndexMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<BaseEntity> entities = jpaRepository.findByValor(valor);
        return entities.stream().map(IndexMapperEntity::toDomain).toList();
    }

    @Override
    public List findByLastUpdate() {
        return List.of();
    }

    @Override
    public List findBetweenDate(Date dataInit, Date dataFim) {
        return List.of();
    }

    @Override
    public List findByDateMax(Date dataMax) {
        return List.of();
    }
}
