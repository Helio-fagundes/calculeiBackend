package application.calculei.usecase.poupanca_antiga;

import application.calculei.infraestructure.entity.PoupAntiga;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.poupanca_antiga.PoupAntigaIndexRepository;
import application.calculei.usecase.poupanca_antiga.port.BuscarPoupAntigoFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdatePoupAntigoFromBc {

    private final BuscarPoupAntigoFromBcPort buscarPoupAntigoFromBcPort;
    private final PoupAntigaIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdatePoupAntigoFromBc(BuscarPoupAntigoFromBcPort buscarPoupAntigoFromBcPort, PoupAntigaIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarPoupAntigoFromBcPort = buscarPoupAntigoFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate hoje = LocalDate.now();
        LocalDate dataMax = repository.findMaxData();
        LocalDate inicio = dataMax != null ? dataMax.plusDays(1) : LocalDate.of(1963, 1, 1);

        if (inicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("POUPANTIGA")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do {

            LocalDate fim = inicio.plusYears(5).minusDays(1);
            if (fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarPoupAntigoFromBcPort.buscar(inicio, fim)){
                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual
                        .divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP)
                        .add(BigDecimal.ONE);

                PoupAntiga poupAntiga = new PoupAntiga();
                poupAntiga.setDataInit(dado.data());
                poupAntiga.setFator(fator);
                poupAntiga.setIndiceBC(indice);

                repository.save(poupAntiga);
            }
            inicio = inicio.plusYears(5);
        }while (inicio.isBefore(hoje));

    }
}
