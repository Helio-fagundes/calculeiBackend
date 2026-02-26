package application.calculei.adapters.gateway.poupanca_nova;

import application.calculei.adapters.mapper.pouponca_nova.PoupNovaMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.PoupNova;
import application.calculei.infraestructure.repository.poupanca_nova.PoupNovaIndexRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PoupJpaRepository implements IndexRepository {

    private final PoupNovaIndexRepository repository;

    public PoupJpaRepository(PoupNovaIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<IndiceBC> findByDescricao(String codigo) {
        return Optional.empty();
    }

    @Override
    public List<Index> findAll() {
        List<PoupNova> listEntity = repository.findAll();
        return listEntity.stream().map(PoupNovaMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByValor(Double valor) {
        List<PoupNova> listEntity = repository.findByValor(valor);
        return listEntity.stream().map(PoupNovaMapperEntity::toDomain).toList();
    }

    @Override
    public Optional<Index> findByLastUpdate() {
        Optional<PoupNova> entity = repository.findAll().stream().max(Comparator.comparing(PoupNova::getDataInit));
        return entity.map(PoupNovaMapperEntity::toDomain);
    }

    @Override
    public List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim) {
        List<PoupNova> listEntity = repository.findByDataInitBetween(dataInit, dataFim);
        return listEntity.stream().map(PoupNovaMapperEntity::toDomain).toList();
    }

    @Override
    public List<Index> findByDataLessThanEqual(LocalDate dataInit) {
        List<PoupNova> listEntity = repository.findByDataInitLessThanEqual(dataInit);
        return listEntity.stream().map(PoupNovaMapperEntity::toDomain).toList();
    }
}
