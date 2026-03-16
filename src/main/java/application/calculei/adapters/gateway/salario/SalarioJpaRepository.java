package application.calculei.adapters.gateway.salario;

import application.calculei.adapters.mapper.salario.SalarioMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Salario;
import application.calculei.infraestructure.repository.salario.SalarioIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SalarioJpaRepository implements IndexRepository {

    private final SalarioIndexRepository repository;

    public SalarioJpaRepository(SalarioIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String serie) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<Salario> listEntity = repository.findAll();
        return listEntity.stream().map(SalarioMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<Salario> entity = repository.findAll().stream().max(Comparator.comparing(Salario::getDataInit));
        return entity.map(SalarioMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<Salario> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(SalarioMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<Salario> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(SalarioMapperEntity::toDomain).toList();
    }
}
