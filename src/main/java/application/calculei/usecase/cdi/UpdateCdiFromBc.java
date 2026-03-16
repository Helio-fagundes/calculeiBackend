package application.calculei.usecase.cdi;

import application.calculei.infraestructure.entity.CDI;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.usecase.cdi.port.BuscarCdiFromBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class UpdateCdiFromBc {

    private final BuscarCdiFromBcPort buscarCdiFromBcPort;
    private final CdiIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateCdiFromBc(BuscarCdiFromBcPort buscarCdiFromBcPort, CdiIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarCdiFromBcPort = buscarCdiFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate dataMax = repository.findMaxDataInit();
        LocalDate inicio = dataMax != null ? dataMax.plusDays(1) : LocalDate.of(1986, 1, 1);
        LocalDate hoje = LocalDate.now();

        if (inicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("CDI")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do{
            LocalDate fim = inicio.plusYears(5).minusDays(1);

            if(fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarCdiFromBcPort.buscar(inicio, fim)){

                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual
                        .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                        .add(BigDecimal.ONE);

                var cdi = new CDI();
                cdi.setDataInit(dado.data());
                cdi.setFator(fator);
                cdi.setIndiceBC(indice);

                repository.save(cdi);
            }
            inicio = inicio.plusYears(5);
        } while (inicio.isBefore(hoje));
    }
}
