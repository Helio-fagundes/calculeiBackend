package application.calculei.usecase.ipcae.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarIpcaeFromBcPort {
    List<DadoBancoCentral> buscar (LocalDate dataInicial);
}
