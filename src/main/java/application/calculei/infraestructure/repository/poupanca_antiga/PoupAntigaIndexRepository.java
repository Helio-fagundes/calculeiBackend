package application.calculei.infraestructure.repository.poupanca_antiga;

import application.calculei.infraestructure.entity.PoupAntiga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PoupAntigaIndexRepository extends JpaRepository<PoupAntiga, Long> {
    List<PoupAntiga> findByValor(Double valor);
    List<PoupAntiga> findByDataInitBetween(Date inicio, Date fim);
    List<PoupAntiga> findByDataInitLessThanEqual(Date dataInit);
}
