package application.calculei.usecase.tbf;

import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.tbf.TbfIndexRepository;
import application.calculei.usecase.tbf.port.BuscarTbfFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateTbfFromBc {

    private final BuscarTbfFromBcPort buscarTbfFromBcPort;
    private final TbfIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateTbfFromBc(BuscarTbfFromBcPort buscarTbfFromBcPort, TbfIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarTbfFromBcPort = buscarTbfFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate lastDate = repository.findMaxDataInit();
        LocalDate hoje = LocalDate.now();
        LocalDate inicio = lastDate != null ? lastDate.plusMonths(1) : LocalDate.of(1995, 1, 1);

        if (inicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("TBF")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do{
            LocalDate fim = inicio.plusYears(5).minusDays(1);

            if(fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarTbfFromBcPort.buscar(inicio, fim)){

                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual
                        .divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP)
                        .add(BigDecimal.ONE);

                var tbf = new application.calculei.infraestructure.entity.TBF();
                tbf.setDataInit(dado.data());
                tbf.setFator(fator);
                tbf.setIndiceBC(indice);

                repository.save(tbf);
            }
            inicio = inicio.plusYears(5);
        }while (inicio.isBefore(hoje));
    }
}
