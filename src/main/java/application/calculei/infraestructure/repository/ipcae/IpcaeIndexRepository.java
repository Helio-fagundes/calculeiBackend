package application.calculei.infraestructure.repository.ipcae;

import application.calculei.infraestructure.entity.IPCAE;

import java.util.Date;
import java.util.List;

public interface IpcaeIndexRepository {
    List<IPCAE> findByValor(Double valor);
    List<IPCAE> findByDataInitBetween(Date inicio, Date fim);
    List<IPCAE> findByDataMax(Date data);
}
