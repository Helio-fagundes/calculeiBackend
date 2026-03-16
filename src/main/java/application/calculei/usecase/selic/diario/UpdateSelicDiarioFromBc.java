package application.calculei.usecase.selic.diario;

import application.calculei.infraestructure.entity.SelicDiario;
import application.calculei.infraestructure.entity.SelicMensal;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicDiarioIndexRepository;
import application.calculei.usecase.selic.diario.port.BuscarSelicDiarioFromBcPort;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class UpdateSelicDiarioFromBc {

    private final BuscarSelicDiarioFromBcPort buscarSelicDiarioFromBcPort;
    private final SelicDiarioIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateSelicDiarioFromBc(BuscarSelicDiarioFromBcPort buscarSelicDiarioFromBcPort, SelicDiarioIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarSelicDiarioFromBcPort = buscarSelicDiarioFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate dataMax = repository.findMaxDataInit();
        LocalDate dataInicio = dataMax != null ? dataMax.plusDays(1) : LocalDate.of(1986, 1, 1);
        LocalDate hoje = LocalDate.now();

        if (dataInicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("SELIC_DIARIO")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do {
            LocalDate fim = dataInicio.plusYears(5).minusDays(1);
            if(fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarSelicDiarioFromBcPort.buscar(dataInicio, fim)){

                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual
                        .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                        .add(BigDecimal.ONE);

                SelicDiario selicDiario = new SelicDiario();
                selicDiario.setDataInit(dado.data());
                selicDiario.setFator(fator);
                selicDiario.setIndiceBC(indice);

                repository.save(selicDiario);
            }
            dataInicio = dataInicio.plusYears(5);
        }while (dataInicio.isBefore(hoje));
    }

}
