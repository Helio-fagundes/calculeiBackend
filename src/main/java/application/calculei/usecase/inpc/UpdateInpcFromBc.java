package application.calculei.usecase.inpc;

import application.calculei.infraestructure.entity.INPC;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.inpc.InpcIndexRepository;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.inpc.port.BuscarInpcFromBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateInpcFromBc {

    private final BuscarInpcFromBcPort buscarInpcFromBcPort;
    private final InpcIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateInpcFromBc(BuscarInpcFromBcPort buscarInpcFromBcPort, InpcIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarInpcFromBcPort = buscarInpcFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate lastDate = repository.findMaxDataInit();
        List<DadoBancoCentral> dados;

        if (lastDate == null){
            dados = buscarInpcFromBcPort.buscar(null);
        } else {
            dados = buscarInpcFromBcPort.buscar(lastDate.plusMonths(1));
        }

        var indice = indicesBcIndexRepository.findBySerie("INPC")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        for (var dado : dados){

            if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                continue;

            BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

            BigDecimal fator = percentual
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            INPC inpc = new INPC();
            inpc.setDataInit(dado.data());
            inpc.setFator(fator);
            inpc.setIndiceBC(indice);

            repository.save(inpc);
        }
    }
}
