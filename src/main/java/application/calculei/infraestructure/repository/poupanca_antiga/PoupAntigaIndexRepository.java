package application.calculei.infraestructure.repository.poupanca_antiga;

import application.calculei.infraestructure.entity.PoupAntiga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PoupAntigaIndexRepository extends JpaRepository<PoupAntiga, Long> {
    List<PoupAntiga> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<PoupAntiga> findByDataInitLessThanEqual(LocalDate dataInit);
}
