package application.calculei.usecase.selic.mensal;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.SelicMensal;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;
import application.calculei.usecase.selic.mensal.dto.CalculateSelicMensalBetweenDateRequest;
import application.calculei.usecase.selic.mensal.dto.CalculateSelicMensalBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateSelicMensalAccumulatedValueBetweenDates {

    private final SelicMensalIndexRepository repository;

    public CalculateSelicMensalAccumulatedValueBetweenDates(SelicMensalIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateSelicMensalBetweenDateResponse calcular(CalculateSelicMensalBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())) {
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }

        List<SelicMensal> listEntity = repository.findByDataInitBetween(request.dateInit().plusMonths(1), request.dateFim().minusMonths(1));
        BigDecimal fatorAcumulado = BigDecimal.ZERO;
        Long dias = DateUtils.businessDays(request.dateInit(), request.dateFim());

        for (SelicMensal entity : listEntity) {
            fatorAcumulado = fatorAcumulado.add(entity.getFator());
        }

        BigDecimal valorFinal =
                BigDecimal.valueOf(request.valor())
                    .multiply(fatorAcumulado)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal percentualAcumulado = fatorAcumulado
                .setScale(2, RoundingMode.HALF_UP);

        return new CalculateSelicMensalBetweenDateResponse(
                request.dateInit(),
                request.dateFim(),
                dias,
                valorFinal,
                percentualAcumulado);
    }
}
