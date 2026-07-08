package application.calculei.usecase.poupanca_nova;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarPoupNovaFromBcPort;
import application.calculei.domain.repository.IndiceBcPort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UpdatePoupNovaFromBc {

    private final IndexRepository repository;
    private final BuscarPoupNovaFromBcPort buscarPoupNovaFromBcPort;
    private final IndiceBcPort  indiceBcPort;

    public UpdatePoupNovaFromBc(
            IndexRepository repository,
            BuscarPoupNovaFromBcPort buscarPoupNovaFromBcPort, IndiceBcPort indiceBcPort
    ) {
        this.repository = repository;
        this.buscarPoupNovaFromBcPort = buscarPoupNovaFromBcPort;
        this.indiceBcPort = indiceBcPort;
    }

    public void execute(){
        LocalDate dataMax = repository.findMaxDataInit();

        LocalDate startDate = dataMax != null
                ? dataMax.plusDays(1)
                : LocalDate.of(2012, 1, 1);

        indiceBcPort.updateLastUpdate("POUPNOVA", startDate);

        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) return;

        while (startDate.isBefore(today) || startDate.isEqual(today)) {
            LocalDate fim = startDate.plusYears(5).minusDays(1);
            if (fim.isAfter(today)) fim = today;

            var dadosBrutosBc = buscarPoupNovaFromBcPort.buscarPoupNovaFromBc(startDate, fim);

            List<Index> listEntity = dadosBrutosBc.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            if (!listEntity.isEmpty()) {
                repository.saveAll(listEntity);

                LocalDate maxSaved = listEntity.stream().map(Index::getDataInit).max(LocalDate::compareTo).orElse(startDate);
                indiceBcPort.updateLastUpdate("POUPNOVA", maxSaved);
            }
            startDate = startDate.plusYears(5);
        }
    }
}
