package application.calculei.infraestructure.repository.tbf;

import application.calculei.infraestructure.entity.TBF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TbfIndexRepository extends JpaRepository<TBF, Long> {
    List<TBF> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<TBF> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(t.dataInit) FROM TBF t")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
