package application.calculei.usecase.inpc;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.INPC;
import application.calculei.infraestructure.repository.inpc.InpcIndexRepository;
import application.calculei.usecase.inpc.dto.CalculateInpcBetweenDateRequest;
import application.calculei.usecase.inpc.dto.CalculateInpcBetweenDateResponse;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateInpcAccumulatedValueBetweenDates {

    private final InpcIndexRepository repository;

    public CalculateInpcAccumulatedValueBetweenDates(InpcIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateInpcBetweenDateResponse calcular(CalculateInpcBetweenDateRequest request){

        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }

        DateUtils  dateUtils = new DateUtils();
        BigDecimal valorAcumulado = BigDecimal.ONE;
        Long dias = dateUtils.businessDays(request.dateInit(), request.dateFim());
        List<INPC> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());

        for (var entity : listEntity){
            valorAcumulado = valorAcumulado.multiply(entity.getFator());
        }
        BigDecimal valorFinal =
                BigDecimal.valueOf(request.valor())
                .multiply(valorAcumulado)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal percentualAcumulado = valorAcumulado
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(6, BigDecimal.ROUND_HALF_UP);

        return new CalculateInpcBetweenDateResponse(request.dateInit(), request.dateFim(), dias, valorFinal, percentualAcumulado);

    }
}
