package application.calculei.usecase.igpdi.port;

import application.calculei.usecase.igpdi.dto.DadoIgpdi;

import java.time.LocalDate;
import java.util.List;

public interface BuscarIgpdiNoBcPort {

    List<DadoIgpdi> buscar(LocalDate dataInicial);
}
