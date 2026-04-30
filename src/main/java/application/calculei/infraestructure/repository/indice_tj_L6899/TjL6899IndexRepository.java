package application.calculei.infraestructure.repository.indice_tj_L6899;

import application.calculei.infraestructure.entity.Indice_TJ_L6899;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TjL6899IndexRepository extends JpaRepository<Indice_TJ_L6899, Long> {
    List<Indice_TJ_L6899> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<Indice_TJ_L6899> findByDataInitLessThanEqual(LocalDate dataInit);
    List<Indice_TJ_L6899> findByDataInit(LocalDate dataInit);
    @Query("SELECT MAX(t.dataInit) FROM Indice_TJ_L6899 t")
    LocalDate findMaxDataInit();
    boolean existsByDataInit(LocalDate novaData);
}
