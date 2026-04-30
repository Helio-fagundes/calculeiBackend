package application.calculei.infraestructure.repository.cdi;

import application.calculei.infraestructure.entity.CDI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CdiIndexRepository extends JpaRepository<CDI, Long> {
    List<CDI> findByDataInitBetween(LocalDate dataInit, LocalDate fim);
    List<CDI> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(c.dataInit) FROM CDI c")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
