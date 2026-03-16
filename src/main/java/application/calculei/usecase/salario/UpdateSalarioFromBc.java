package application.calculei.usecase.salario;

import application.calculei.infraestructure.entity.Salario;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.salario.SalarioIndexRepository;
import application.calculei.usecase.salario.port.BuscarSalarioFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateSalarioFromBc {

    private final BuscarSalarioFromBcPort buscarSalarioFromBcPort;
    private final SalarioIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateSalarioFromBc(BuscarSalarioFromBcPort buscarSalarioFromBcPort, SalarioIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarSalarioFromBcPort = buscarSalarioFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate dataMax = repository.findMaxDataInit();
        LocalDate inicio = dataMax != null ? dataMax.plusDays(1) : LocalDate.of(1986, 1, 1);
        LocalDate hoje = LocalDate.now();

        if (inicio.isAfter(hoje)) return;

        var indice = indicesBcIndexRepository.findBySerie("SALARIO")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do {
            LocalDate fim = inicio.plusYears(5).minusDays(1);
            if(fim.isAfter(hoje)) fim = hoje;

            for (var dado : buscarSalarioFromBcPort.buscar(inicio, fim)){
                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual;


                var salario = new Salario();
                salario.setDataInit(dado.data());
                salario.setFator(fator);
                salario.setIndiceBC(indice);

                repository.save(salario);
            }
            inicio = inicio.plusYears(5);
        }while (inicio.isBefore(hoje));
    }
}
