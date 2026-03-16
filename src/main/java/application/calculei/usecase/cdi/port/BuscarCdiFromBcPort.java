package application.calculei.usecase.cdi.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarCdiFromBcPort {

    List<DadoBancoCentral> buscar(LocalDate dataInicial, LocalDate dataFinal);
}
