package application.calculei.infraestructure.repository.inpc;

import application.calculei.infraestructure.entity.INPC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InpcIndexRepository extends JpaRepository<INPC, Long> {
    List<INPC> findByValor(Double valor);
    List<INPC> findByDataInitBetween(Date inicio, Date fim);
    List<INPC> findByDataInitLessThanEqual(Date dataInit);
}
