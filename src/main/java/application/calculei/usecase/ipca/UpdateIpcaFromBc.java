package application.calculei.usecase.ipca;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarIpcaFromBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateIpcaFromBc {

    private final BuscarIpcaFromBcPort buscarIpcaFromBcPort;
    private final IndexRepository repository;

    public UpdateIpcaFromBc(BuscarIpcaFromBcPort buscarIpcaFromBcPort, IndexRepository repository) {
        this.buscarIpcaFromBcPort = buscarIpcaFromBcPort;
        this.repository = repository;
    }

    public void execute() {

        LocalDate startDate = repository.findByLastUpdate()
                .map(index -> index.getDataInit().plusMonths(1))
                .orElse(null);

        var bruteDate = buscarIpcaFromBcPort.buscar(startDate);

        List<Index> listEntity = bruteDate.stream()
                .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                .toList();

        if(!listEntity.isEmpty()){
            repository.saveAll(listEntity);
        }
    }
}
