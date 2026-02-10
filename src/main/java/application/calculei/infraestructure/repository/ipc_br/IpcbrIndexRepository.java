package application.calculei.infraestructure.repository.ipc_br;

import application.calculei.infraestructure.entity.IPCBR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IpcbrIndexRepository extends JpaRepository<IPCBR, Long> {
    List<IPCBR> findByValor(Double valor);
    List<IPCBR> findByDataInitBetween(Date inicio, Date fim);
    List<IPCBR> findByDataInitLessThanEqual(Date dataInit);
}
