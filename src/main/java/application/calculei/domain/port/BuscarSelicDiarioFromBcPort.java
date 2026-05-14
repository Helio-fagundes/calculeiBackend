package application.calculei.domain.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarSelicDiarioFromBcPort {

    List<DadoBancoCentral> buscar(LocalDate dataInicial, LocalDate dataFinal);
}
