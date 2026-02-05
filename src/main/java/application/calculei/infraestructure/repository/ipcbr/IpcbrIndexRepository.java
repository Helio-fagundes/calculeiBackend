package application.calculei.infraestructure.repository.ipcbr;

import application.calculei.infraestructure.entity.IPCBR;

import java.util.Date;
import java.util.List;

public interface IpcbrIndexRepository {
    List<IPCBR> findByValor(Double valor);
    List<IPCBR> findByDataInitBetween(Date inicio, Date fim);
    List<IPCBR> findByDataMax(Date data);
}
