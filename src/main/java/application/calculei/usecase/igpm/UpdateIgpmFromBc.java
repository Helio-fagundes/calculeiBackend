package application.calculei.usecase.igpm;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;

import application.calculei.domain.port.BuscarIgpmFromBcPort;


import java.time.LocalDate;
import java.util.List;

public class UpdateIgpmFromBc {

    private final BuscarIgpmFromBcPort buscarIgpmFromBcPort;
    private final IndexRepository repository;

    public UpdateIgpmFromBc(BuscarIgpmFromBcPort buscarIgpmFromBcPort, IndexRepository repository) {
        this.buscarIgpmFromBcPort = buscarIgpmFromBcPort;
        this.repository = repository;
    }

    public void execute() {

        LocalDate startDate = repository.findByLastUpdate()
                .map(index -> index.getDataInit().plusMonths(1))
                .orElse(null);

        var bruteDate = buscarIgpmFromBcPort.buscar(startDate);

        List<Index> listEntity = bruteDate.stream()
                .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                .toList();

        if (!listEntity.isEmpty()) {
            repository.saveAll(listEntity);
        }
    }
}
