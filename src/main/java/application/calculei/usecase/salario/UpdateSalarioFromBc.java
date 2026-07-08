package application.calculei.usecase.salario;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarSalarioFromBcPort;
import application.calculei.domain.repository.IndiceBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateSalarioFromBc {

    private final BuscarSalarioFromBcPort buscarSalarioFromBcPort;
    private final IndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public UpdateSalarioFromBc(
            BuscarSalarioFromBcPort buscarSalarioFromBcPort,
            IndexRepository repository, IndiceBcPort indiceBcPort
    ) {
        this.buscarSalarioFromBcPort = buscarSalarioFromBcPort;
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    public void execute(){
        LocalDate dataMax = repository.findMaxDataInit();

        LocalDate startDate = dataMax != null
                ? dataMax.plusDays(1)
                : LocalDate.of(1986, 1, 1);

        indiceBcPort.updateLastUpdate("SALARIO", startDate);

        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) return;

        while (startDate.isBefore(today) || startDate.isEqual(today)) {
            LocalDate fim = startDate.plusYears(5).minusDays(1);
            if (fim.isAfter(today)) fim = today;

            var dadosBrutosBc = buscarSalarioFromBcPort.buscar(startDate, fim);

            List<Index> listEntity = dadosBrutosBc.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            if (!listEntity.isEmpty()) {
                repository.saveAll(listEntity);

                LocalDate maxSaved = listEntity.stream().map(Index::getDataInit).max(LocalDate::compareTo).orElse(startDate);
                indiceBcPort.updateLastUpdate("SALARIO", maxSaved);
            }
            startDate = startDate.plusYears(5);
        }
    }
}
