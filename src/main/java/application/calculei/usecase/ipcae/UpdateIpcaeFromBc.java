package application.calculei.usecase.ipcae;

import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca_e.IpcaeIndexRepository;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.ipcae.port.BuscarIpcaeFromBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateIpcaeFromBc {

    private final IpcaeIndexRepository repository;
    private final BuscarIpcaeFromBcPort buscarIpcaeFromBcPort;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateIpcaeFromBc(IpcaeIndexRepository repository, BuscarIpcaeFromBcPort buscarIpcaeFromBcPort, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.repository = repository;
        this.buscarIpcaeFromBcPort = buscarIpcaeFromBcPort;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate lastDate = repository.findMaxDateInit();
        List<DadoBancoCentral> dados;

        if (lastDate == null){
            dados = buscarIpcaeFromBcPort.buscar(null);
        } else {
            dados = buscarIpcaeFromBcPort.buscar(lastDate.plusMonths(1));
        }

        IndiceBC indice = indicesBcIndexRepository.findBySerie("IPCAE")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        for(var dado : dados){
            if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                continue;

            BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

            BigDecimal fator = percentual
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            IPCAE ipcae = new IPCAE();
            ipcae.setDataInit(dado.data());
            ipcae.setFator(fator);
            ipcae.setIndiceBC(indice);

            repository.save(ipcae);
        }
    }
}
