package application.calculei.usecase.poupanca_nova;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.PoupNova;
import application.calculei.infraestructure.repository.poupanca_nova.PoupNovaIndexRepository;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateRequest;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateResponse;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculatePoupNovaAccumulatedValueBetweenDates {

    private final PoupNovaIndexRepository repository;

    public CalculatePoupNovaAccumulatedValueBetweenDates(PoupNovaIndexRepository repository) {
        this.repository = repository;
    }

    public CalculatePoupNovaBetweenDateResponse calcular(CalculatePoupNovaBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }

        List<PoupNova> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim().minusMonths(1));
        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long dias = DateUtils.businessDays(request.dateInit(), request.dateFim());

        for (var entity : listEntity){
            fatorAcumulado = fatorAcumulado.multiply(entity.getFator());
        }

        BigDecimal valorFinal =
                BigDecimal.valueOf(request.valor())
                        .multiply(fatorAcumulado)
                        .setScale(2 , BigDecimal.ROUND_HALF_UP);

        BigDecimal percentualAcumulado = fatorAcumulado
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(6, BigDecimal.ROUND_HALF_UP);

        return new CalculatePoupNovaBetweenDateResponse(request.dateInit(), request.dateFim(), dias, valorFinal, percentualAcumulado);
    }
}
