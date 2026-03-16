package application.calculei.usecase.ipcbr;

import application.calculei.infraestructure.entity.IPCBR;
import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipc_br.IpcbrIndexRepository;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.ipcbr.port.BuscarIpcbrFromBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateIpcbrFromBc {

    private final BuscarIpcbrFromBcPort buscarIpcbrNoBcPort;
    private final IpcbrIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateIpcbrFromBc(BuscarIpcbrFromBcPort buscarIpcbrNoBcPort, IpcbrIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarIpcbrNoBcPort = buscarIpcbrNoBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate lastDate = repository.findMaxDataInit();
        List<DadoBancoCentral> dados;

        if (lastDate == null){
            dados = buscarIpcbrNoBcPort.buscar(null);
        } else {
            dados = buscarIpcbrNoBcPort.buscar(lastDate.plusMonths(1));
        }

        IndiceBC indice = indicesBcIndexRepository.findBySerie("IPCBR")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        for (var dado : dados){

            if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                continue;

            BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

            BigDecimal fator = percentual
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            IPCBR ipcbr = new IPCBR();
            ipcbr.setDataInit(dado.data());
            ipcbr.setFator(fator);
            ipcbr.setIndiceBC(indice);

            repository.save(ipcbr);
        }
    }
}
