package application.calculei.usecase.poupanca_antiga;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.PoupAntiga;
import application.calculei.infraestructure.repository.poupanca_antiga.PoupAntigaIndexRepository;
import application.calculei.usecase.poupanca_antiga.dto.CalculatePoupAntigoBetweenDateRequest;
import application.calculei.usecase.poupanca_antiga.dto.CalculatePoupAntigoBetweenDateResponse;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculatePoupAntigoAccumulatedValueBetweenDates {

    private final PoupAntigaIndexRepository repository;

    public CalculatePoupAntigoAccumulatedValueBetweenDates(PoupAntigaIndexRepository repository) {
        this.repository = repository;
    }

    public CalculatePoupAntigoBetweenDateResponse calcular(CalculatePoupAntigoBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("Data final deve ser posterior a data inicial");
        }

        DateUtils dateUtils = new DateUtils();
        List<PoupAntiga> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());
        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long dias = dateUtils.businessDays(request.dateInit(), request.dateFim());

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

        return new CalculatePoupAntigoBetweenDateResponse(request.dateInit(), request.dateFim(), dias, valorFinal, percentualAcumulado);
    }
}
