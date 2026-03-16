package application.calculei.infraestructure.repository.poupanca_antiga;

import application.calculei.infraestructure.entity.PoupAntiga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PoupAntigaIndexRepository extends JpaRepository<PoupAntiga, Long> {
    List<PoupAntiga> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<PoupAntiga> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(p.dataInit) FROM PoupAntiga p")
    LocalDate findMaxData();
    Boolean existsByDataInit(LocalDate dataInit);
}
