package application.calculei.infraestructure.repository.ipca_e;

import application.calculei.infraestructure.entity.IPCAE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IpcaeIndexRepository extends JpaRepository<IPCAE, Long> {
    List<IPCAE> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<IPCAE> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(x.dataInit) FROM IPCAE x")
    LocalDate findMaxDateInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
