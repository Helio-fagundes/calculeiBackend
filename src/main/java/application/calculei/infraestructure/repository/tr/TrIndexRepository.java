package application.calculei.infraestructure.repository.tr;

import application.calculei.infraestructure.entity.TR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TrIndexRepository extends JpaRepository<TR, Long> {
    List<TR> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<TR> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(t.dataInit) FROM TR t")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
