package application.calculei.infraestructure.repository.selic;

import application.calculei.infraestructure.entity.Selic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SelicIndexRepository extends JpaRepository<Selic, Long> {
    List<Selic> findByValor(Double valor);
    List<Selic> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<Selic> findByDataInitLessThanEqual(LocalDate dataInit);
}
