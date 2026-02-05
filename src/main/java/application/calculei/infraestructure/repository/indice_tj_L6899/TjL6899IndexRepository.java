package application.calculei.infraestructure.repository.indice_tj_L6899;

import application.calculei.infraestructure.entity.Indice_TJ_L6899;

import java.util.Date;
import java.util.List;

public interface TjL6899IndexRepository {
    List<Indice_TJ_L6899> findByValor(Double valor);
    List<Indice_TJ_L6899> findByDataInitBetween(Date inicio, Date fim);
    List<Indice_TJ_L6899> findByDataMax(Date data);
}
