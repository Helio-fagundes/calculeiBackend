package application.calculei.usecase.ipca_tl;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarIpcaTlFromBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateIpcaTlFromBc {

    private final BuscarIpcaTlFromBcPort buscarIpcaTlFromBcPort;
    private final IndexRepository repository;

    public UpdateIpcaTlFromBc(
            BuscarIpcaTlFromBcPort buscarIpcaTlFromBcPort,
            IndexRepository repository
    ) {
        this.buscarIpcaTlFromBcPort = buscarIpcaTlFromBcPort;
        this.repository = repository;
    }

    public void execute() {
        LocalDate dateMax = repository.findMaxDataInit();

        LocalDate startDate = dateMax != null
                ? dateMax.plusDays(1)
                : LocalDate.of(2023, 1, 1);

        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) return;

        while (startDate.isBefore(today) || startDate.isEqual(today)) {
            LocalDate fim = startDate.plusYears(5).minusDays(1);
            if (fim.isAfter(today)) fim = today;

            var dadosBrutosBc = buscarIpcaTlFromBcPort.buscar(startDate, fim);

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
