package application.calculei.usecase.ipcae;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarIpcaeFromBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateIpcaeFromBc {

    private final IndexRepository repository;
    private final BuscarIpcaeFromBcPort buscarIpcaeFromBcPort;

    public UpdateIpcaeFromBc(IndexRepository repository, BuscarIpcaeFromBcPort buscarIpcaeFromBcPort) {
        this.repository = repository;
        this.buscarIpcaeFromBcPort = buscarIpcaeFromBcPort;
    }

    public void execute() {
        LocalDate dateMax = repository.findMaxDataInit();

        LocalDate startDate = dateMax != null
                ? dateMax.plusMonths(1)
                : LocalDate.of(2023, 1, 1);

        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) return;

        while (startDate.isBefore(today) || startDate.isEqual(today)) {
            LocalDate fim = startDate.plusYears(5).minusDays(1);
            if (fim.isAfter(today)) fim = today;

            var dadosBrutosBc = buscarIpcaeFromBcPort.buscar(startDate, fim);

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
