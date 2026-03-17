package application.calculei.usecase.taxa_legal.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarTaxaLegalFromBcPort {
    List<DadoBancoCentral> buscar(LocalDate dataInicio, LocalDate dataFinal);
}
