package application.calculei.infraestructure.repository.cdi;

import application.calculei.infraestructure.entity.CDI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CdiIndexRepository extends JpaRepository<CDI, Long> {
    List<CDI> findByValor(Double valor);
    List<CDI> findByDataInitBetween(Date dataInit, Date fim);
    List<CDI> findByDataInitLessThanEqual(Date dataInit);
}
