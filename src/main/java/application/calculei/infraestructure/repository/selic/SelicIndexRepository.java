package application.calculei.infraestructure.repository.selic;

import application.calculei.infraestructure.entity.Selic;

import java.util.Date;
import java.util.List;

public interface SelicIndexRepository {
    List<Selic> findByValor(Double valor);
    List<Selic> findByDataInitBetween(Date inicio, Date fim);
    List<Selic> findByDataMax(Date data);
}
