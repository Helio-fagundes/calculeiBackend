package application.calculei.infraestructure.repository.indice_tj_L11960_selic;

import application.calculei.infraestructure.entity.Indice_TJ_L11960_Selic;

import java.util.Date;
import java.util.List;

public interface TjL11960SelicIndexRepository {
    List<Indice_TJ_L11960_Selic> findByValor(Double valor);
    List<Indice_TJ_L11960_Selic> findByDataInitBetween(Date inicio, Date fim);
    List<Indice_TJ_L11960_Selic> findByDataMax(Date data);
}
