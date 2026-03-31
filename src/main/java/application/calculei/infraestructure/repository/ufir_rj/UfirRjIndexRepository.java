package application.calculei.infraestructure.repository.ufir_rj;

import application.calculei.infraestructure.entity.UfirRJ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UfirRjIndexRepository extends JpaRepository<UfirRJ, Long> {
    List<UfirRJ> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<UfirRJ> findByDataInitLessThanEqual(LocalDate dataInit);
    List<UfirRJ> findByDataInit(LocalDate dataInit);
}
