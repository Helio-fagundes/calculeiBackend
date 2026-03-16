package application.calculei.domain.repository;

import application.calculei.domain.models.Index;
import application.calculei.infraestructure.entity.IndiceBC;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IndexRepository {

    Optional<IndiceBC> findBySerie(String serie);
    List<Index> findAll();
    Optional<Index> findByLastUpdate();
    List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim);
    List<Index> findByDataLessThanEqual(LocalDate dataInit);
}
