package application.calculei.infraestructure.repository.indice_tj_l6899;

import application.calculei.infraestructure.entity.IndiceTjL6899;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TjL6899IndexRepository extends JpaRepository<IndiceTjL6899, Long> {
    List<IndiceTjL6899> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<IndiceTjL6899> findByDataInitLessThanEqual(LocalDate dataInit);
    List<IndiceTjL6899> findByDataInit(LocalDate dataInit);
    @Query("SELECT MAX(t.dataInit) FROM IndiceTjL6899 t")
    LocalDate findMaxDataInit();
    boolean existsByDataInit(LocalDate novaData);
}
