package application.calculei.infraestructure.repository.ufir_rj;

import application.calculei.infraestructure.entity.UfirRJ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UfirRjIndexRepository extends JpaRepository<UfirRJ, Long> {
    List<UfirRJ> findByValor(Double valor);
    List<UfirRJ> findByDataInitBetween(Date inicio, Date fim);
    List<UfirRJ> findByDataInitLessThanEqual(Date dataInit);
}
