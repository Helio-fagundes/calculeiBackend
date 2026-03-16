package application.calculei.usecase.salario;

import application.calculei.infraestructure.entity.Salario;
import application.calculei.infraestructure.repository.salario.SalarioIndexRepository;
import application.calculei.usecase.salario.dto.CalculateSalarioBetweenDateRequest;
import application.calculei.usecase.salario.dto.CalculateSalarioBetweenDateResponse;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateSalarioAccumulatedValueBetweenDates {

    private final SalarioIndexRepository salarioIndexRepository;

    public CalculateSalarioAccumulatedValueBetweenDates(SalarioIndexRepository salarioIndexRepository) {
        this.salarioIndexRepository = salarioIndexRepository;
    }

    public CalculateSalarioBetweenDateResponse calcular(CalculateSalarioBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("A data final deve ser posterior a data inicial.");
        }

        List<Salario> listEntity = salarioIndexRepository.findByDataInitBetween(request.dateInit(), request.dateFim());
        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long diasContados = ChronoUnit.DAYS.between(request.dateInit(), request.dateFim());

        for (Salario entity : listEntity){
            fatorAcumulado = fatorAcumulado.multiply(entity.getFator());
        }

        BigDecimal valorFinal =
                BigDecimal.valueOf(request.valor())
                        .multiply(fatorAcumulado)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal percentualAcumulado = fatorAcumulado
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(6, BigDecimal.ROUND_HALF_UP);

        return new CalculateSalarioBetweenDateResponse(request.dateInit(), request.dateFim(), diasContados, valorFinal, percentualAcumulado);
    }
}
