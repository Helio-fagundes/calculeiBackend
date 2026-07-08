package application.calculei.usecase.igpm;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;

import application.calculei.domain.port.BuscarIgpmFromBcPort;
import application.calculei.domain.repository.IndiceBcPort;


import java.time.LocalDate;
import java.util.List;

public class UpdateIgpmFromBc {

    private final BuscarIgpmFromBcPort buscarIgpmFromBcPort;
    private final IndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public UpdateIgpmFromBc(BuscarIgpmFromBcPort buscarIgpmFromBcPort, IndexRepository repository, IndiceBcPort indiceBcPort) {
        this.buscarIgpmFromBcPort = buscarIgpmFromBcPort;
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    public void execute() {
        LocalDate dataMax = repository.findMaxDataInit();
        
        LocalDate startDate = dataMax != null
                ? dataMax.plusMonths(1)
                : LocalDate.of(1980, 1, 1);

        indiceBcPort.updateLastUpdate("IGPM", startDate);

        var bruteDate = buscarIgpmFromBcPort.buscar(startDate);

        if (!bruteDate.isEmpty()){
            List<Index> listEntity = bruteDate.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            repository.saveAll(listEntity);

            LocalDate maxSaved = listEntity.stream().map(Index::getDataInit).max(LocalDate::compareTo).orElse(startDate);
            indiceBcPort.updateLastUpdate("IGPM", maxSaved);
        }
    }
}
