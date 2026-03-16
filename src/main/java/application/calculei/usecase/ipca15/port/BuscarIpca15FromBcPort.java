package application.calculei.usecase.ipca15.port;

import application.calculei.usecase.dto.DadoBancoCentral;

import java.time.LocalDate;
import java.util.List;

public interface BuscarIpca15FromBcPort {
    List<DadoBancoCentral> buscar(LocalDate dataInicial);
}
