package application.calculei.usecase.ipca15;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.IPCA15;
import application.calculei.infraestructure.repository.ipca_15.Ipca15IndexRepository;
import application.calculei.usecase.ipca15.dto.CalculateIpca15BetweenDateRequest;
import application.calculei.usecase.ipca15.dto.CalculateIpca15BetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateIpca15AccumulatedValueBetweenDates {

    private final Ipca15IndexRepository repository;

    public CalculateIpca15AccumulatedValueBetweenDates(Ipca15IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIpca15BetweenDateResponse calcular(CalculateIpca15BetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("a data inicio não pode ser superior a data final.");
        }
        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long dias = DateUtils.businessDays(request.dateInit(), request.dateFim());
        List<IPCA15> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());

        for (var entity : listEntity){
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

        return new CalculateIpca15BetweenDateResponse(request.dateInit(), request.dateFim(), dias ,valorFinal, percentualAcumulado);
    }
}
