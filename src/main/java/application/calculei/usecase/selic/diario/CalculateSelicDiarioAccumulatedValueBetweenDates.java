package application.calculei.usecase.selic.diario;

import application.calculei.infraestructure.entity.SelicDiario;
import application.calculei.infraestructure.repository.selic.SelicDiarioIndexRepository;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateRequest;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateSelicDiarioAccumulatedValueBetweenDates {

    private final SelicDiarioIndexRepository repository;


    public CalculateSelicDiarioAccumulatedValueBetweenDates(SelicDiarioIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateSelicDiarioBetweenDateResponse calcular(CalculateSelicDiarioBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }

        List<SelicDiario> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());
        Long dias = ChronoUnit.DAYS.between(request.dateInit(), request.dateFim());
        BigDecimal fatorAcumulado = BigDecimal.ONE;

        for (SelicDiario entity : listEntity){
            fatorAcumulado = fatorAcumulado.multiply(entity.getFator());
        }

        BigDecimal valorFinal =
                BigDecimal.valueOf(request.valor())
                .multiply(fatorAcumulado)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal percentualAcumulado = fatorAcumulado
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(6, RoundingMode.HALF_UP);

        return new CalculateSelicDiarioBetweenDateResponse(request.dateInit(), request.dateFim(), dias, valorFinal, percentualAcumulado);
    }
}
