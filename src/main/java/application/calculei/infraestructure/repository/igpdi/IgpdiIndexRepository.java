package application.calculei.infraestructure.repository.igpdi;

import application.calculei.infraestructure.entity.IGPDI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IgpdiIndexRepository extends JpaRepository<IGPDI, Long> {
    List<IGPDI> findByDataInitBetween(LocalDate dataInit, LocalDate fim);
    List<IGPDI> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(x.dataInit) FROM IGPDI x")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
