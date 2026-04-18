package application.calculei.infraestructure.repository.selic;

import application.calculei.infraestructure.entity.SelicMensal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SelicMensalIndexRepository extends JpaRepository<SelicMensal, Long> {
    List<SelicMensal> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<SelicMensal> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(s.dataInit) FROM SelicMensal s")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
    SelicMensal findByDataInit(LocalDate dataInit);
}
