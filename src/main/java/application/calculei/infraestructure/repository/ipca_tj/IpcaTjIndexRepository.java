package application.calculei.infraestructure.repository.ipca_tj;

import application.calculei.infraestructure.entity.IPCA_TJ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface IpcaTjIndexRepository extends JpaRepository<IPCA_TJ, Long> {
    List<IPCA_TJ> findByValor(Double valor);
    List<IPCA_TJ> findByDataInitBetween(Date inicio, Date fim);
    List<IPCA_TJ> findByDataInitLessThanEqual(Date dataInit);
}
