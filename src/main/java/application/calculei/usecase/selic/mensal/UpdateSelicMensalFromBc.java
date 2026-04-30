package application.calculei.usecase.selic.mensal;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarSelicMensalFromBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateSelicMensalFromBc {

    private final BuscarSelicMensalFromBcPort buscarSelicMensalFromBcPort;
    private final IndexRepository repository;

    public UpdateSelicMensalFromBc(BuscarSelicMensalFromBcPort buscarSelicMensalFromBcPort, IndexRepository repository) {
        this.buscarSelicMensalFromBcPort = buscarSelicMensalFromBcPort;
        this.repository = repository;
    }

    public void execute() {
        LocalDate dataMax = repository.findMaxDataInit();
        LocalDate startDate = dataMax != null
                ? dataMax.plusDays(1)
                : LocalDate.of(1986, 1, 1);

        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) return;

        while (startDate.isBefore(today) || startDate.isEqual(today)) {
            LocalDate fim = startDate.plusYears(5).minusDays(1);
            if (fim.isAfter(today)) fim = today;

            var dadosBrutosBc = buscarSelicMensalFromBcPort.buscar(startDate, fim);

            List<Index> listEntity = dadosBrutosBc.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            if (!listEntity.isEmpty()) {
                repository.saveAll(listEntity);
            }
            startDate = startDate.plusYears(5);
        }
    }
}
