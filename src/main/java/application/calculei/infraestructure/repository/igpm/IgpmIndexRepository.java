package application.calculei.infraestructure.repository.igpm;


import application.calculei.infraestructure.entity.IGPM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IgpmIndexRepository extends JpaRepository<IGPM, Long> {
    List<IGPM> findByDataInitBetween(LocalDate dataInit, LocalDate fim);
    List<IGPM> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(x.dataInit) FROM IGPM x")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
