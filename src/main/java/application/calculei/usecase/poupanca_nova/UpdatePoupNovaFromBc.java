package application.calculei.usecase.poupanca_nova;

import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.poupanca_nova.PoupNovaIndexRepository;
import application.calculei.usecase.poupanca_nova.port.BuscarPoupNovaFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdatePoupNovaFromBc {

    private final PoupNovaIndexRepository repository;
    private final BuscarPoupNovaFromBcPort buscarPoupNovaFromBcPort;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdatePoupNovaFromBc(PoupNovaIndexRepository repository, BuscarPoupNovaFromBcPort buscarPoupNovaFromBcPort, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.buscarPoupNovaFromBcPort = buscarPoupNovaFromBcPort;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate dataMax = repository.findMaxData();
        LocalDate inicio = dataMax != null ? dataMax.plusDays(1) : LocalDate.of(2012, 1, 1);
        LocalDate hoje = LocalDate.now();

        if (inicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("POUPNOVA")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do {
            LocalDate fim = inicio.plusYears(5).minusDays(1);
            if(fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarPoupNovaFromBcPort.buscarPoupNovaFromBc(inicio, fim)){
                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual
                        .divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP)
                        .add(BigDecimal.ONE);

                var poupNova = new application.calculei.infraestructure.entity.PoupNova();
                poupNova.setDataInit(dado.data());
                poupNova.setFator(fator);
                poupNova.setIndiceBC(indice);

                repository.save(poupNova);
            }
            inicio = inicio.plusYears(5);
        }while (inicio.isBefore(hoje));
    }
}
