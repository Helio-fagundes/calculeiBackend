package application.calculei.infraestructure.repository.ufir_rj;

import application.calculei.infraestructure.entity.UfirRJ;

import java.util.Date;
import java.util.List;

public interface UfirRjIndexRepository {
    List<UfirRJ> findByValor(Double valor);
    List<UfirRJ> findByDataInitBetween(Date inicio, Date fim);
    List<UfirRJ> findByDataMax(Date data);
}
