package application.calculei.usecase.tbf;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.TBF;
import application.calculei.infraestructure.repository.tbf.TbfIndexRepository;
import application.calculei.usecase.tbf.dto.CalculateTbfBetweenDateRequest;
import application.calculei.usecase.tbf.dto.CalculateTbfBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateTbfAccumulatedValueBetweenDates {

    private final TbfIndexRepository repository;

    public CalculateTbfAccumulatedValueBetweenDates(TbfIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateTbfBetweenDateResponse calcular(CalculateTbfBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }
        List<TBF> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());
        Long dias = DateUtils.businessDays(request.dateInit(), request.dateFim());
        BigDecimal valorAcumulado = BigDecimal.ONE;

        for (var entity : listEntity) {
            valorAcumulado = valorAcumulado.multiply(entity.getFator());
        }

        BigDecimal valorFinal =
                BigDecimal.valueOf(request.valor())
                        .multiply(valorAcumulado)
                        .setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal percentualAcumulado = valorAcumulado
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(6, RoundingMode.HALF_UP);

        return new CalculateTbfBetweenDateResponse(request.dateInit(), request.dateFim(), dias, valorFinal, percentualAcumulado);
    }
}
