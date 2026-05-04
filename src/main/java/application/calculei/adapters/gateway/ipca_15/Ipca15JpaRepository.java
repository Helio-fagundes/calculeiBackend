package application.calculei.adapters.gateway.ipca_15;

import application.calculei.adapters.mapper.ipca_15.Ipca15MapperEntity;
import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.IPCA15;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Indice_TJ_L11960_Selic;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca_15.Ipca15IndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Ipca15JpaRepository implements IndexRepository {

    private final Ipca15IndexRepository repository;
    private final IndicesBcIndexRepository  indicesBcIndexRepository;

    public Ipca15JpaRepository(
            Ipca15IndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
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

    @Override
    public void saveAll(List<Index> listEntity) {

        IndiceBC indiceBC = indicesBcIndexRepository.findBySerie("IPCA15")
                .orElseThrow(() -> new RuntimeException("Índice IPCA15 não encontrado na base de dados."));

        List<LocalDate> dateToSave = listEntity
                .stream()
                .map(Index::getDataInit)
                .toList();

        List<LocalDate> dateExist = repository.findByDataInitBetween(
                        dateToSave.stream().min(LocalDate::compareTo).orElseThrow(),
                        dateToSave.stream().max(LocalDate::compareTo).orElseThrow())
                .stream()
                .map(IPCA15::getDataInit)
                .toList();

        List<IPCA15> entity = listEntity
                .stream()
                .filter(index -> !dateExist.contains(index.getDataInit()))
                .map(index -> {
                    IPCA15 ipca15 = new IPCA15();
                    ipca15.setDataInit(index.getDataInit());
                    ipca15.setFator(index.getFator());
                    ipca15.setIndiceBC(indiceBC);
                    return ipca15;
                })
                .toList();

        if (!entity.isEmpty()) {
            repository.saveAll(entity);
        }
    }

    @Override
    public LocalDate findMaxDataInit() {
        return repository.findAll().stream()
                .map(IPCA15::getDataInit)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("Nenhuma data encontrada para o indice IPCA15"));
    }

    @Override
    public Index findDataInit(LocalDate dataInit) {
        IPCA15 ipca15 = repository.findByDataInit(dataInit);
        if (ipca15 == null){
            throw new DataNotFoundException("Índice IPCA15 não encontrado para a data: " + dataInit);
        }
        return Ipca15MapperEntity.toDomain(ipca15);
    }
}
