package application.calculei.infraestructure.repository.ipca_tj;

import application.calculei.infraestructure.entity.IPCA_TJ;

import java.util.Date;
import java.util.List;

public interface IpcaTjIndexRepository {
    List<IPCA_TJ> findByValor(Double valor);
    List<IPCA_TJ> findByDataInitBetween(Date inicio, Date fim);
    List<IPCA_TJ> findByDataMax(Date data);
}
