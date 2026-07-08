package application.calculei.usecase.ipca;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarIpcaFromBcPort;
import application.calculei.domain.repository.IndiceBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateIpcaFromBc {

    private final BuscarIpcaFromBcPort buscarIpcaFromBcPort;
    private final IndexRepository repository;
    private final IndiceBcPort indiceBcPort;

    public UpdateIpcaFromBc(BuscarIpcaFromBcPort buscarIpcaFromBcPort, IndexRepository repository, IndiceBcPort indiceBcPort) {
        this.buscarIpcaFromBcPort = buscarIpcaFromBcPort;
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    public void execute() {

        LocalDate dataMax = repository.findMaxDataInit();

        LocalDate startDate = dataMax != null
                ? dataMax.plusMonths(1)
                : LocalDate.of(1970, 1, 1);

        indiceBcPort.updateLastUpdate("IPCA", startDate);

        var bruteDate = buscarIpcaFromBcPort.buscar(startDate);

        if (!bruteDate.isEmpty()){
            List<Index> listEntity = bruteDate.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            repository.saveAll(listEntity);

            LocalDate maxSaved = listEntity.stream().map(Index::getDataInit).max(LocalDate::compareTo).orElse(startDate);
            indiceBcPort.updateLastUpdate("IPCA", maxSaved);
        }
    }
}
