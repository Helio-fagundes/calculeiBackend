package application.calculei.infraestructure.repository.indice_tj_L11960;


import application.calculei.infraestructure.entity.Indice_TJ_L11960;

import java.util.Date;
import java.util.List;

public interface TjL11960IndexRepository {
    List<Indice_TJ_L11960> findByValor(Double valor);
    List<Indice_TJ_L11960> findByDataInitBetween(Date inicio, Date fim);
    List<Indice_TJ_L11960> findByDataMax(Date data);
}
