package application.calculei.infraestructure.repository.igpm;


import application.calculei.infraestructure.entity.IGPM;

import java.util.Date;
import java.util.List;

public interface IgpmIndexRepository {
    List<IGPM> findByValor(Double valor);
    List<IGPM> findByDataInitBetween(Date inicio, Date fim);
    List<IGPM> findByDataMax(Date data);
}
