package application.calculei.usecase.cdi;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarCdiFromBcPort;
import application.calculei.domain.repository.IndiceBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateCdiFromBc {

    private final BuscarCdiFromBcPort buscarCdiFromBcPort;
    private final IndexRepository repository;
    private final IndiceBcPort  indiceBCPort;

    public UpdateCdiFromBc(
            BuscarCdiFromBcPort buscarCdiFromBcPort,
            IndexRepository repository, IndiceBcPort indiceBCPort
    ) {
        this.buscarCdiFromBcPort = buscarCdiFromBcPort;
        this.repository = repository;
        this.indiceBCPort = indiceBCPort;
    }

    public void execute(){
        LocalDate dataMax = repository.findMaxDataInit();

        LocalDate inicio = dataMax != null
                ? dataMax.plusDays(1)
                : LocalDate.of(1985, 1, 1);

        indiceBCPort.updateLastUpdate("CDI", inicio);

        LocalDate hoje = LocalDate.now();

        if (inicio.isAfter(hoje)) return;

        while (inicio.isBefore(hoje) || inicio.isEqual(hoje)) {
            LocalDate fim = inicio.plusYears(5).minusDays(1);
            if (fim.isAfter(hoje)) fim = hoje;

            var dadosBrutosBc = buscarCdiFromBcPort.buscar(inicio, fim);

            List<Index> listEntity = dadosBrutosBc.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            LocalDate maxSaved = listEntity.stream().map(Index::getDataInit).max(LocalDate::compareTo).orElse(inicio);

            if (!listEntity.isEmpty()) {
                repository.saveAll(listEntity);
            }
            indiceBCPort.updateLastUpdate("CDI", maxSaved);

            inicio = inicio.plusYears(5);
        }
    }
}
