package application.calculei.infraestructure.repository.ipc_br;

import application.calculei.infraestructure.entity.IPCBR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IpcbrIndexRepository extends JpaRepository<IPCBR, Long> {
    List<IPCBR> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<IPCBR> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(x.dataInit) FROM IPCBR x")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
