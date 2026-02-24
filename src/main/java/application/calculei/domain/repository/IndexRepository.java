package application.calculei.domain.repository;

import application.calculei.domain.models.Index;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IndexRepository {

    List<Index> findAll();
    List<Index> findByValor(Double valor);
    Optional<Index> findByLastUpdate();
    List<Index> findByDataInitBetween(LocalDate dataInit, LocalDate dataFim);
    List<Index> findByDataLessThanEqual(LocalDate dataInit);
}
