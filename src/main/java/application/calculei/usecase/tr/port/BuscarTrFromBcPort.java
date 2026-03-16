package application.calculei.usecase.tr.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarTrFromBcPort {
    List<DadoBancoCentral> buscar(LocalDate dataInicial, LocalDate dataFinal);
}
