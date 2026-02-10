package application.calculei.adapters.gateway.cdi;

import application.calculei.adapters.mapper.cdi.CdiMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.mappedClass.BaseEntity;
import application.calculei.infraestructure.entity.CDI;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CdiJpaRepository implements IndexRepository {

    private final CdiIndexRepository repository;

    public CdiJpaRepository(CdiIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Index> findAll() {
        List<CDI> entities = repository.findAll();
        return entities.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<CDI> entities = repository.findByValor(valor);
        return entities.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<CDI> entity = repository.findAll().stream().max(Comparator.comparing(CDI::getDataInit));
        return entity.map(CdiMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(Date dataInit, Date dataFim) {
        List<CDI> entityList = repository.findByDataInitBetween(dataInit, dataFim);
        return entityList.stream().map(CdiMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(Date dataInit) {
        List<CDI> entityList = repository.findByDataInitLessThanEqual(dataInit);
        return entityList.stream().map(CdiMapperEntity::toDomain).toList();
    }
}
