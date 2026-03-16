package application.calculei.usecase.tr;

import application.calculei.infraestructure.entity.TR;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.tr.TrIndexRepository;
import application.calculei.usecase.tr.port.BuscarTrFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateTrFromBc {

    private final BuscarTrFromBcPort buscarTrFromBc;
    private final TrIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateTrFromBc(BuscarTrFromBcPort buscarTrFromBc, TrIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarTrFromBc = buscarTrFromBc;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate dataMax = repository.findMaxDataInit();
        LocalDate inicio = dataMax != null ? dataMax.plusDays(1) : LocalDate.of(1990, 1, 1);
        LocalDate hoje = LocalDate.now();

        if (inicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("TR")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do {
            LocalDate fim = inicio.plusYears(5).minusDays(1);
            if(fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarTrFromBc.buscar(inicio, fim)){

                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual
                        .divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP)
                        .add(BigDecimal.ONE);

                TR tr = new TR();
                tr.setDataInit(dado.data());
                tr.setFator(fator);
                tr.setIndiceBC(indice);

                repository.save(tr);
            }
            inicio = inicio.plusYears(5);
        }while (inicio.isBefore(hoje));
    }
}
