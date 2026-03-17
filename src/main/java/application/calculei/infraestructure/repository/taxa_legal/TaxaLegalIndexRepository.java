package application.calculei.infraestructure.repository.taxa_legal;

import application.calculei.infraestructure.entity.TaxaLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TaxaLegalIndexRepository extends JpaRepository<TaxaLegal, Long> {
    List<TaxaLegal> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<TaxaLegal> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(x.dataInit) FROM TaxaLegal x")
    LocalDate findMaxDateInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
