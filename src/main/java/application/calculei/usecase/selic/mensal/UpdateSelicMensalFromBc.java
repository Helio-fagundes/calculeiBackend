package application.calculei.usecase.selic.mensal;

import application.calculei.infraestructure.entity.SelicMensal;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;
import application.calculei.usecase.selic.mensal.port.BuscarSelicMensalFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateSelicMensalFromBc {

    private final BuscarSelicMensalFromBcPort buscarSelicMensalFromBcPort;
    private final SelicMensalIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateSelicMensalFromBc(BuscarSelicMensalFromBcPort buscarSelicMensalFromBcPort, SelicMensalIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarSelicMensalFromBcPort = buscarSelicMensalFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate dataMax = repository.findMaxDataInit();
        LocalDate inicio = dataMax != null ? dataMax.plusDays(1) : LocalDate.of(1986, 1, 1);
        LocalDate hoje = LocalDate.now();

        if (inicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("SELIC_MENSAL")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do {
            LocalDate fim = inicio.plusYears(5).minusDays(1);
            if(fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarSelicMensalFromBcPort.buscar(inicio, fim)){

                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual;

                SelicMensal selicMensal = new SelicMensal();
                selicMensal.setDataInit(dado.data());
                selicMensal.setFator(fator);
                selicMensal.setIndiceBC(indice);

                repository.save(selicMensal);
            }
            inicio = inicio.plusYears(5);
        }while (inicio.isBefore(hoje));
    }
}
