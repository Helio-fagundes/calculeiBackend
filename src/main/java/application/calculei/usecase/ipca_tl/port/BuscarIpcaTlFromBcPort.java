package application.calculei.usecase.ipca_tl.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarIpcaTlFromBcPort {
    List<DadoBancoCentral> buscar(LocalDate dataInicio, LocalDate dataFinal);
}
