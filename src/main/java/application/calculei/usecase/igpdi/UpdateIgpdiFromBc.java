package application.calculei.usecase.igpdi;

import application.calculei.infraestructure.entity.IGPDI;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.igpdi.port.BuscarIgpdiFromBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateIgpdiFromBc {

    private final BuscarIgpdiFromBcPort buscarIgpdiFromBcPort;
    private final IgpdiIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateIgpdiFromBc(BuscarIgpdiFromBcPort buscarIgpdiFromBcPort, IgpdiIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarIgpdiFromBcPort = buscarIgpdiFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){

        LocalDate lastDate = repository.findMaxDataInit();
        List<DadoBancoCentral> dados;

        if (lastDate == null){
            dados = buscarIgpdiFromBcPort.buscar(null);
        } else {
            dados = buscarIgpdiFromBcPort.buscar(lastDate.plusMonths(1));
        }

        IndiceBC indice = indicesBcIndexRepository.findBySerie("IGPDI")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        for (var dado : dados){

            if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                continue;

            BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

            BigDecimal fator = percentual
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            IGPDI igpdi = new IGPDI();
            igpdi.setDataInit(dado.data());
            igpdi.setFator(fator);
            igpdi.setIndiceBC(indice);

            repository.save(igpdi);
        }
    }
}
