package application.calculei.usecase.ipca;

import application.calculei.infraestructure.entity.IPCA;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca.IpcaIndexRepository;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.ipca.port.BuscarIpcaFromBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateIpcaFromBc {

    private final BuscarIpcaFromBcPort buscarIpcaFromBcPort;
    private final IpcaIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateIpcaFromBc(BuscarIpcaFromBcPort buscarIpcaFromBcPort, IpcaIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarIpcaFromBcPort = buscarIpcaFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate lastDate = repository.findMaxDataInit();
        List<DadoBancoCentral> dados;

        if (lastDate == null){
            dados = buscarIpcaFromBcPort.buscar(null);
        } else {
            dados = buscarIpcaFromBcPort.buscar(lastDate.plusMonths(1));
        }

        var indice = indicesBcIndexRepository.findBySerie("IPCA")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        for (var dado : dados){

            if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                continue;

            BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

            BigDecimal fator = percentual
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            IPCA ipca = new IPCA();
            ipca.setDataInit(dado.data());
            ipca.setFator(fator);
            ipca.setIndiceBC(indice);

            repository.save(ipca);
        }
    }
}
