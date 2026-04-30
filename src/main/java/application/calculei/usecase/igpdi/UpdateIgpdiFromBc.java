package application.calculei.usecase.igpdi;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarIgpdiFromBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateIgpdiFromBc {

    private final BuscarIgpdiFromBcPort buscarIgpdiFromBcPort;
    private final IndexRepository repository;

    public UpdateIgpdiFromBc(
            BuscarIgpdiFromBcPort buscarIgpdiFromBcPort,
            IndexRepository repository
    ) {
        this.buscarIgpdiFromBcPort = buscarIgpdiFromBcPort;
        this.repository = repository;
    }

    public void execute(){

        LocalDate startDate = repository
                .findByLastUpdate()
                .map(index -> index.getDataInit().plusMonths(1))
                .orElse(null);

        var bruteDate = buscarIgpdiFromBcPort.buscar(startDate);

        List<Index> listEntity = bruteDate.stream()
                .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                .toList();

        if (!listEntity.isEmpty()) {
            repository.saveAll(listEntity);
        }
    }
}
