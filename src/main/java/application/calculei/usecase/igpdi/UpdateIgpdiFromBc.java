package application.calculei.usecase.igpdi;

import application.calculei.infraestructure.entity.IGPDI;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.usecase.igpdi.dto.DadoIgpdi;
import application.calculei.usecase.igpdi.port.BuscarIgpdiNoBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateIgpdiFromBc {

    private final BuscarIgpdiNoBcPort buscarIgpdiNoBcPort;
    private final IgpdiIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateIgpdiFromBc(BuscarIgpdiNoBcPort buscarIgpdiNoBcPort, IgpdiIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarIgpdiNoBcPort = buscarIgpdiNoBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void execute(){

        LocalDate lastDate = repository.findMaxDataInit();
        List<DadoIgpdi> dados;

        if (lastDate == null){
            dados = buscarIgpdiNoBcPort.buscar(null);
        } else {
            dados = buscarIgpdiNoBcPort.buscar(lastDate.plusMonths(1));
        }

        for (var dado : dados){

            if (repository.existsByDataInit(dado.data()))
                continue;

            BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

            BigDecimal fator = percentual
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            IndiceBC indice = indicesBcIndexRepository.findByDescricao("IGP-DI")
                    .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

            IGPDI igpdi = new IGPDI();
            igpdi.setDataInit(dado.data());
            igpdi.setFator(fator.doubleValue());
            igpdi.setIndiceBC(indice);

            repository.save(igpdi);
        }
    }
}
