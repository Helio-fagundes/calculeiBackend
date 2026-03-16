package application.calculei.usecase.igpdi;

import application.calculei.infraestructure.entity.IGPDI;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.igpdi.dto.CalculateIgpdiBetweenDateRequest;
import application.calculei.usecase.igpdi.dto.CalculateIgpdiBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateIgpdiAccumulatedValueBetweenDates {

    private final IgpdiIndexRepository repository;

    public CalculateIgpdiAccumulatedValueBetweenDates(IgpdiIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIgpdiBetweenDateResponse calcular(CalculateIgpdiBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("a data inicio não pode ser superior a data final.");
        }

        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long diasContados = ChronoUnit.DAYS.between(request.dateInit(), request.dateFim());
        List<IGPDI> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());

        for (IGPDI entity : listEntity){
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

        return new CalculateIgpdiBetweenDateResponse(request.dateInit(), request.dateFim(), diasContados, valorFinal, percentualAcumulado);
    }
}
