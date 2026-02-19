package application.calculei.infraestructure.repository.poupanca_nova;

import application.calculei.infraestructure.entity.PoupNova;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PoupNovaIndexRepository extends JpaRepository<PoupNova, Long> {
    List<PoupNova> findByValor(Double valor);
    List<PoupNova> findByDataInitBetween(Date inicio, Date fim);
    List<PoupNova> findByDataInitLessThanEqual(Date dataInit);
}
