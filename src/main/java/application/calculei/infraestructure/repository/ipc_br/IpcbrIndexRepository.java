package application.calculei.infraestructure.repository.ipc_br;

import application.calculei.infraestructure.entity.IPCBR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IpcbrIndexRepository extends JpaRepository<IPCBR, Long> {
    List<IPCBR> findByValor(Double valor);
    List<IPCBR> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<IPCBR> findByDataInitLessThanEqual(LocalDate dataInit);
}
