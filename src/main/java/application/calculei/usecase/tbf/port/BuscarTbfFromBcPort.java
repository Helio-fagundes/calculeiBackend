package application.calculei.usecase.tbf.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarTbfFromBcPort {
    List<DadoBancoCentral> buscar(LocalDate dataInicial, LocalDate dataFinal);
}
