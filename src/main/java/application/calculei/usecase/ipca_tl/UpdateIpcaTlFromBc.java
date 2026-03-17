package application.calculei.usecase.ipca_tl;

import application.calculei.infraestructure.entity.IPCA_Tl;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca_tl.IpcaTlIndexRepository;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.ipca_tl.port.BuscarIpcaTlFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class UpdateIpcaTlFromBc {

    private final BuscarIpcaTlFromBcPort buscarIpcaTlFromBcPort;
    private final IpcaTlIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateIpcaTlFromBc(BuscarIpcaTlFromBcPort buscarIpcaTlFromBcPort, IpcaTlIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarIpcaTlFromBcPort = buscarIpcaTlFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update() {
        LocalDate dateMax = repository.findMaxDateInit();
        LocalDate hoje = LocalDate.now();
        LocalDate dataInicio = dateMax != null ? dateMax.plusDays(1) : LocalDate.of(2023, 1, 1);

        if (dataInicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("IPCA_TL")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do {
            LocalDate fim = dataInicio.plusYears(5).minusDays(1);
            if (fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarIpcaTlFromBcPort.buscar(dataInicio, fim)) {
                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual
                        .divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP)
                        .add(BigDecimal.ONE);

                var ipcaTl = new IPCA_Tl();
                ipcaTl.setDataInit(dado.data());
                ipcaTl.setFator(fator);
                ipcaTl.setIndiceBC(indice);

                repository.save(ipcaTl);
            }
            dataInicio.plusYears(5);
        } while (dataInicio.isBefore(hoje));
    }
}
