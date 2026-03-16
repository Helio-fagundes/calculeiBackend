package application.calculei.usecase.ipcbr.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarIpcbrFromBcPort {
    List<DadoBancoCentral> buscar(LocalDate dataInicial);
}
