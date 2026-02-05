package application.calculei.infraestructure.repository.imab;


import application.calculei.infraestructure.entity.IMAB;

import java.util.Date;
import java.util.List;

public interface ImabIndexRepository {
    List<IMAB> findByValor(Double valor);
    List<IMAB> findByDataInitBetween(Date inicio, Date fim);
    List<IMAB> findByDataMax(Date data);
}
