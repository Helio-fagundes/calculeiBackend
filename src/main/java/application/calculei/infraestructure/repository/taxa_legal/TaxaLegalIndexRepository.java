package application.calculei.infraestructure.repository.taxa_legal;

import application.calculei.infraestructure.entity.TaxaLegal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaxaLegalIndexRepository extends JpaRepository<TaxaLegal, Long> {
    List<TaxaLegal> findByValor(Double valor);
    List<TaxaLegal> findByDataInitBetween(Date inicio, Date fim);
    List<TaxaLegal> findByDataInitLessThanEqual(Date dataInit);
}
