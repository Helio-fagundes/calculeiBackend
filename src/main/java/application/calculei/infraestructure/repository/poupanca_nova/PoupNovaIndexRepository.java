package application.calculei.infraestructure.repository.poupanca_nova;

import application.calculei.infraestructure.entity.PoupNova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PoupNovaIndexRepository extends JpaRepository<PoupNova, Long> {
    List<PoupNova> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<PoupNova> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(p.dataInit) FROM PoupNova p")
    LocalDate findMaxData();
     Boolean existsByDataInit(LocalDate dataInit);
}
