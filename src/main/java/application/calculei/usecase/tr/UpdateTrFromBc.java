package application.calculei.usecase.tr;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarTrFromBcPort;
import application.calculei.domain.repository.IndiceBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateTrFromBc {

    private final BuscarTrFromBcPort buscarTrFromBc;
    private final IndexRepository repository;
    private final IndiceBcPort indiceBcPort;

    public UpdateTrFromBc(BuscarTrFromBcPort buscarTrFromBc, IndexRepository repository, IndiceBcPort indiceBcPort) {
        this.buscarTrFromBc = buscarTrFromBc;
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    public void execute() {
        LocalDate dataMax = repository.findMaxDataInit();

        LocalDate startDate = dataMax != null
                ? dataMax.plusDays(1)
                : LocalDate.of(1990, 1, 1);

        indiceBcPort.updateLastUpdate("TR", dataMax);

        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) return;

        while (startDate.isBefore(today) || startDate.isEqual(today)) {
            LocalDate fim = startDate.plusYears(5).minusDays(1);
            if (fim.isAfter(today)) fim = today;

            var dadosBrutosBc = buscarTrFromBc.buscar(startDate, fim);

            List<Index> listEntity = dadosBrutosBc.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            if (!listEntity.isEmpty()) {
                repository.saveAll(listEntity);

                LocalDate maxSaved = listEntity.stream().map(Index::getDataInit).max(LocalDate::compareTo).orElse(startDate);
                indiceBcPort.updateLastUpdate("TR", maxSaved);
            }
            startDate = startDate.plusYears(5);
        }
    }
}
