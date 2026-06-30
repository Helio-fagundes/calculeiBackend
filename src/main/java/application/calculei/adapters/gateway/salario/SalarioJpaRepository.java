package application.calculei.adapters.gateway.salario;

import application.calculei.adapters.mapper.salario.SalarioMapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Salario;
import application.calculei.infraestructure.repository.salario.SalarioIndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Comparator;

import java.util.List;
import java.util.Optional;

@Transactional
public class SalarioJpaRepository implements IndexRepository {

    private final SalarioIndexRepository repository;
    private final IndiceBcPort indiceBcPort;

    public SalarioJpaRepository(SalarioIndexRepository repository, IndiceBcPort indiceBcPort) {
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
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

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indiceBcPort.findBySerie("SALARIO")
                .orElseThrow(() -> new RuntimeException("Índice Salario não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(Salario::getDataInit)
                .toList();

        List<Salario> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    Salario salario = new Salario();
                    salario.setDataInit(index.getDataInit());
                    salario.setFator(index.getFator());
                    salario.setIndiceBC(indiceBC);
                    return salario;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(Salario::getDataInit)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        Salario salario = repository.findByDataInit(dataInit);
        if (salario == null) {
            throw new DataNotFoundException("Índice Salario não encontrado para a data: " + dataInit);
        }
        return SalarioMapperEntity.toDomain(salario);
    }
}
