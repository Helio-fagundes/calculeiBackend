package application.calculei.usecase.igpm.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarIgpmFromBcPort {
    List<DadoBancoCentral> buscar(LocalDate dataInicial);
}
