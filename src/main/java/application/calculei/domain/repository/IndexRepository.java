package application.calculei.domain.repository;

import application.calculei.domain.models.Index;

import java.util.Date;
import java.util.List;

public interface IndexRepository {

    List<Index> findAll();
    List<Index> findByValor(Double valor);
    List<Index> findByLastUpdate();
    List<Index> findBetweenDate(Date dataInit, Date dataFim);
    List<Index> findByDateMax(Date dataMax);
}
