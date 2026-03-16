package application.calculei.usecase.igpm;

import application.calculei.infraestructure.entity.IGPM;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.igpm.IgpmIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.igpm.port.BuscarIgpmFromBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateIgpmFromBc {

    private final BuscarIgpmFromBcPort buscarIgpmFromBcPort;
    private final IgpmIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateIgpmFromBc(BuscarIgpmFromBcPort buscarIgpmFromBcPort, IgpmIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarIgpmFromBcPort = buscarIgpmFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate lastDate = repository.findMaxDataInit();
        List<DadoBancoCentral> dados;

        if (lastDate == null){
            dados = buscarIgpmFromBcPort.buscar(null);
        } else {
            dados = buscarIgpmFromBcPort.buscar(lastDate.plusMonths(1));
        }

        IndiceBC indice = indicesBcIndexRepository.findBySerie("IGPM")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        for (var dado : dados){

            if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                continue;

            BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

            BigDecimal fator = percentual
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            IGPM igpm = new IGPM();
            igpm.setDataInit(dado.data());
            igpm.setFator(fator);
            igpm.setIndiceBC(indice);

            repository.save(igpm);
        }
    }
}
