package application.calculei.adapters.gateway;

import application.calculei.adapters.mapper.CdiMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.BaseEntity;
import application.calculei.infraestructure.entity.CDI;
import application.calculei.infraestructure.repository.CdiIndexRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CdiJpaRepository implements IndexRepository {

    private final CdiIndexRepository jpaRepository;

    public CdiJpaRepository(CdiIndexRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Index> findAll() {
        List<CDI> entities = jpaRepository.findAll();
        return entities.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<CDI> entities = jpaRepository.findByValor(valor);
        return entities.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<CDI> entity = jpaRepository.findAll().stream().max(Comparator.comparing(BaseEntity::getDataInit));
        return entity.map(CdiMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(Date dataInit, Date dataFim) {
        List<CDI> entityList = jpaRepository.findByDataInitBetween(dataInit, dataFim);
        return entityList.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDateMax(Date dataMax) {
        List<CDI> entityList = jpaRepository.findByDataMax(dataMax);
        return entityList.stream().map(CdiMapperEntity::toDomain).toList();
    }
}
