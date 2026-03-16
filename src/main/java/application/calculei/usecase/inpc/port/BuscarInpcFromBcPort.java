package application.calculei.usecase.inpc.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarInpcFromBcPort {

    List<DadoBancoCentral> buscar(LocalDate dataInicial);
}
