package application.calculei.infraestructure.repository.taxa_legal;

import application.calculei.infraestructure.entity.TaxaLegal;

import java.util.Date;
import java.util.List;

public interface TaxaLegalIndexRepository {
    List<TaxaLegal> findByValor(Double valor);
    List<TaxaLegal> findByDataInitBetween(Date inicio, Date fim);
    List<TaxaLegal> findByDataMax(Date data);
}
