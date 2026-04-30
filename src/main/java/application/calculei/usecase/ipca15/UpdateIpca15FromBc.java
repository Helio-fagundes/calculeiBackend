package application.calculei.usecase.ipca15;

import application.calculei.domain.models.Index;
import application.calculei.domain.port.BuscarIpca15FromBcPort;
import application.calculei.domain.repository.IndexRepository;

import java.time.LocalDate;
import java.util.List;

public class UpdateIpca15FromBc {

    private final BuscarIpca15FromBcPort buscarIpca15FromBcPort;
    private final IndexRepository repository;

    public UpdateIpca15FromBc(BuscarIpca15FromBcPort buscarIpca15FromBcPort, IndexRepository repository) {
        this.buscarIpca15FromBcPort = buscarIpca15FromBcPort;
        this.repository = repository;
    }

    public void execute() {

        LocalDate startDate = repository.findByLastUpdate()
                .map(index -> index.getDataInit().plusMonths(1))
                .orElse(null);

        var bruteDate = buscarIpca15FromBcPort.buscar(startDate);

        List<Index> listEntity = bruteDate.stream()
                .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                .toList();

        if (!listEntity.isEmpty()) {
            repository.saveAll(listEntity);
        }
    }
}
