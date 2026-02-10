package application.calculei.infraestructure.repository.tr;

import application.calculei.infraestructure.entity.TR;

import java.util.Date;
import java.util.List;

public interface TrIndexRepository {
    List<TR> findByValor(Double valor);
    List<TR> findByDataInitBetween(Date inicio, Date fim);
    List<TR> findByDataInitLessThanEqual(Date dataInit);
}
