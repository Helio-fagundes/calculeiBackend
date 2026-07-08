package application.calculei.usecase.igpdi;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarIgpdiFromBcPort;
import application.calculei.domain.repository.IndiceBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateIgpdiFromBc {

    private final BuscarIgpdiFromBcPort buscarIgpdiFromBcPort;
    private final IndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public UpdateIgpdiFromBc(
            BuscarIgpdiFromBcPort buscarIgpdiFromBcPort,
            IndexRepository repository, IndiceBcPort indiceBcPort
    ) {
        this.buscarIgpdiFromBcPort = buscarIgpdiFromBcPort;
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    public void execute(){
        LocalDate dataMax = repository.findMaxDataInit();

        LocalDate startDate = dataMax != null
                ? dataMax.plusMonths(1)
                : LocalDate.of(1944, 1, 1);

        indiceBcPort.updateLastUpdate("IGPDI", startDate);

        var bruteDate = buscarIgpdiFromBcPort.buscar(startDate);

        if (!bruteDate.isEmpty()) {
            List<Index> listEntity = bruteDate.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            repository.saveAll(listEntity);

            LocalDate maxSaved = listEntity.stream().map(Index::getDataInit).max(LocalDate::compareTo).orElse(startDate);
            indiceBcPort.updateLastUpdate("IGPDI", maxSaved);
        } else {
            if (dataMax != null) {
                indiceBcPort.updateLastUpdate("IGPDI", dataMax);
            }
        }
    }
}