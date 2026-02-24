package application.calculei.infraestructure.repository.inpc;

import application.calculei.infraestructure.entity.INPC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface InpcIndexRepository extends JpaRepository<INPC, Long> {
    List<INPC> findByValor(Double valor);
    List<INPC> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<INPC> findByDataInitLessThanEqual(LocalDate dataInit);
}
