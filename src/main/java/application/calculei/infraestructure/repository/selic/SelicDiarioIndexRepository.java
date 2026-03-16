package application.calculei.infraestructure.repository.selic;

import application.calculei.infraestructure.entity.SelicDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SelicDiarioIndexRepository extends JpaRepository<SelicDiario, Long> {
    List<SelicDiario> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<SelicDiario> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(s.dataInit) FROM SelicDiario s")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
