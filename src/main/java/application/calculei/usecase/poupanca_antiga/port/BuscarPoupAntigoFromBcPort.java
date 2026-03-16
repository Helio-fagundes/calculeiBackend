package application.calculei.usecase.poupanca_antiga.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarPoupAntigoFromBcPort {
    List<DadoBancoCentral> buscar(LocalDate dataInicio, LocalDate dataFim);
}
