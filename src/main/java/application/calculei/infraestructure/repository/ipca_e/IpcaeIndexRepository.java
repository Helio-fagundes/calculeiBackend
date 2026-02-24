package application.calculei.infraestructure.repository.ipca_e;

import application.calculei.infraestructure.entity.IPCAE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IpcaeIndexRepository extends JpaRepository<IPCAE, Long> {
    List<IPCAE> findByValor(Double valor);
    List<IPCAE> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<IPCAE> findByDataInitLessThanEqual(LocalDate dataInit);
}
