package application.calculei.usecase.cdi;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.port.BuscarCdiFromBcPort;

import java.time.LocalDate;
import java.util.List;

public class UpdateCdiFromBc {

    private final BuscarCdiFromBcPort buscarCdiFromBcPort;
    private final IndexRepository repository;

    public UpdateCdiFromBc(
            BuscarCdiFromBcPort buscarCdiFromBcPort,
            IndexRepository repository
    ) {
        this.buscarCdiFromBcPort = buscarCdiFromBcPort;
        this.repository = repository;
    }

    public void execute(){
        LocalDate dataMax = repository.findMaxDataInit();

        LocalDate inicio = dataMax != null
                ? dataMax.plusDays(1)
                : LocalDate.of(1986, 1, 1);

        LocalDate hoje = LocalDate.now();

        if (inicio.isAfter(hoje)) return;

        while (inicio.isBefore(hoje) || inicio.isEqual(hoje)) {
            LocalDate fim = inicio.plusYears(5).minusDays(1);
            if (fim.isAfter(hoje)) fim = hoje;

            var dadosBrutosBc = buscarCdiFromBcPort.buscar(inicio, fim);

            List<Index> listEntity = dadosBrutosBc.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            if (!listEntity.isEmpty()) {
                repository.saveAll(listEntity);
            }

            inicio = inicio.plusYears(5);
        }
    }
}
