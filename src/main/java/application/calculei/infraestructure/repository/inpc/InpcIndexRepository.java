package application.calculei.infraestructure.repository.inpc;

import application.calculei.infraestructure.entity.INPC;

import java.util.Date;
import java.util.List;

public interface InpcIndexRepository {
    List<INPC> findByValor(Double valor);
    List<INPC> findByDataInitBetween(Date inicio, Date fim);
    List<INPC> findByDataMax(Date data);
}
