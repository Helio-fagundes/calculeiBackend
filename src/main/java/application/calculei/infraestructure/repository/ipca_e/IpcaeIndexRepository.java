package application.calculei.infraestructure.repository.ipca_e;

import application.calculei.infraestructure.entity.IPCAE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IpcaeIndexRepository extends JpaRepository<IPCAE, Long> {
    List<IPCAE> findByValor(Double valor);
    List<IPCAE> findByDataInitBetween(Date inicio, Date fim);
    List<IPCAE> findByDataInitLessThanEqual(Date dataInit);
}
