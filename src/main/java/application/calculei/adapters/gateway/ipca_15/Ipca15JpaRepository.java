package application.calculei.adapters.gateway.ipca_15;

import application.calculei.adapters.mapper.ipca_15.Ipca15MapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCA15;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.ipca_15.Ipca15IndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Ipca15JpaRepository implements IndexRepository {

    private final Ipca15IndexRepository repository;

    public Ipca15JpaRepository(Ipca15IndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findBySerie(String codigo) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<IPCA15> listEntity = repository.findAll();
        return listEntity.stream().map(Ipca15MapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<IPCA15> entity = repository.findAll().stream().max(Comparator.comparing(IPCA15::getDataInit));
        return entity.map(Ipca15MapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<IPCA15> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(Ipca15MapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<IPCA15> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(Ipca15MapperEntity::toDomain).toList();
    }
}
