package application.calculei.usecase.selic.mensal.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarSelicMensalFromBcPort {

    List<DadoBancoCentral> buscar(LocalDate dataInicial, LocalDate dataFinal);
}
