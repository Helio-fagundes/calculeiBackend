package application.calculei.usecase.igpm;

import application.calculei.infraestructure.entity.IGPM;
import application.calculei.infraestructure.repository.igpm.IgpmIndexRepository;
import application.calculei.usecase.igpm.dto.CalculateIgpmBetweenDateRequest;
import application.calculei.usecase.igpm.dto.CalculateIgpmBetweenDateResponse;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateIgpmAccumulatedValueBetweenDates {

    private final IgpmIndexRepository repository;

    public CalculateIgpmAccumulatedValueBetweenDates(IgpmIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIgpmBetweenDateResponse calcular(CalculateIgpmBetweenDateRequest request){

        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("a data inicio não pode ser superior a data final.");
        }

        List<IGPM> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());
        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long diasContados = ChronoUnit.DAYS.between(request.dateInit(), request.dateFim());

        for (IGPM entity : listEntity){
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

        return new CalculateIgpmBetweenDateResponse(request.dateInit(), request.dateFim(), diasContados, valorFinal, percentualAcumulado);
    }
}
