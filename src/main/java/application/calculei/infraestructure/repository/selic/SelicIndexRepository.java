package application.calculei.infraestructure.repository.selic;

import application.calculei.infraestructure.entity.Selic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SelicIndexRepository extends JpaRepository<Selic, Long> {
    List<Selic> findByValor(Double valor);
    List<Selic> findByDataInitBetween(Date inicio, Date fim);
    List<Selic> findByDataInitLessThanEqual(Date dataInit);
}
