package application.calculei.usecase.tbf;

import application.calculei.domain.models.Index;
import application.calculei.domain.port.BuscarTbfFromBcPort;
import application.calculei.domain.repository.IndexRepository;

import java.time.LocalDate;
import java.util.List;

public class UpdateTbfFromBc {

    private final BuscarTbfFromBcPort buscarTbfFromBcPort;
    private final IndexRepository repository;

    public UpdateTbfFromBc(BuscarTbfFromBcPort buscarTbfFromBcPort, IndexRepository repository) {
        this.buscarTbfFromBcPort = buscarTbfFromBcPort;
        this.repository = repository;
    }

    public void execute(){
        LocalDate lastDate = repository.findMaxDataInit();
        LocalDate startDate = lastDate != null
                ? lastDate.plusMonths(1)
                : LocalDate.of(1995, 1, 1);

        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) return;

        while (startDate.isBefore(today) || startDate.isEqual(today)) {
            LocalDate fim = startDate.plusYears(5).minusDays(1);
            if (fim.isAfter(today)) fim = today;

            var dadosBrutosBc = buscarTbfFromBcPort.buscar(startDate, fim);

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
