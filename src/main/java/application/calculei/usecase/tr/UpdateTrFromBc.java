package application.calculei.usecase.tr;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.entity.TR;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.tr.TrIndexRepository;
import application.calculei.domain.port.BuscarTrFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class UpdateTrFromBc {

    private final BuscarTrFromBcPort buscarTrFromBc;
    private final IndexRepository repository;

    public UpdateTrFromBc(BuscarTrFromBcPort buscarTrFromBc, IndexRepository repository) {
        this.buscarTrFromBc = buscarTrFromBc;
        this.repository = repository;
    }

    public void execute() {
        LocalDate dataMax = repository.findMaxDataInit();
        LocalDate startDate = dataMax != null
                ? dataMax.plusDays(1)
                : LocalDate.of(1990, 1, 1);

        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) return;

        while (startDate.isBefore(today) || startDate.isEqual(today)) {
            LocalDate fim = startDate.plusYears(5).minusDays(1);
            if (fim.isAfter(today)) fim = today;

            var dadosBrutosBc = buscarTrFromBc.buscar(startDate, fim);

            List<Index> listEntity = dadosBrutosBc.stream()
                    .map(dado -> Index.calculatePercentual(dado.data(), dado.valor()))
                    .toList();

            if (!listEntity.isEmpty()) {
                repository.saveAll(listEntity);
            }
            startDate = startDate.plusYears(5);
        }
    }
}
