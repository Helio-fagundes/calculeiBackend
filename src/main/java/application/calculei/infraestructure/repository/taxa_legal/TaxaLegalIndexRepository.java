package application.calculei.infraestructure.repository.taxa_legal;

import application.calculei.infraestructure.entity.TaxaLegal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TaxaLegalIndexRepository extends JpaRepository<TaxaLegal, Long> {
    List<TaxaLegal> findByValor(Double valor);
    List<TaxaLegal> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<TaxaLegal> findByDataInitLessThanEqual(LocalDate dataInit);
}
