package application.calculei.usecase.cdi;

import application.calculei.infraestructure.entity.CDI;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.usecase.cdi.dto.CalculateBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateAccumulatedValueBetweenDates {

    private final CdiIndexRepository repository;

    public CalculateAccumulatedValueBetweenDates(CdiIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateBetweenDateResponse calcular(CalculateBetweenDateRequest request){

        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("Data fim não pode ser menor que a data inicio");
        }

        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long diasContados = ChronoUnit.DAYS.between(request.dateInit(), request.dateFim());
        List<CDI> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());

        for (CDI entity : listEntity){
            fatorAcumulado = fatorAcumulado.multiply(BigDecimal.valueOf(entity.getFator()));
        }
        BigDecimal valorFinal =
                BigDecimal.valueOf(request.valor())
                .multiply(fatorAcumulado)
                .setScale(10, RoundingMode.HALF_UP);

        return new CalculateBetweenDateResponse(request.dateInit(), request.dateFim(), diasContados, valorFinal);
    }
}
