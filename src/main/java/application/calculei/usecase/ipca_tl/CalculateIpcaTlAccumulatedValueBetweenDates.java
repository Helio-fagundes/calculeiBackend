package application.calculei.usecase.ipca_tl;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.IPCA_Tl;
import application.calculei.infraestructure.repository.ipca_tl.IpcaTlIndexRepository;
import application.calculei.usecase.ipca_tl.dto.CalculateIpcaTlBetweenDateRequest;
import application.calculei.usecase.ipca_tl.dto.CalculateIpcaTlBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateIpcaTlAccumulatedValueBetweenDates {

    private final IpcaTlIndexRepository repository;

    public CalculateIpcaTlAccumulatedValueBetweenDates(IpcaTlIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIpcaTlBetweenDateResponse calcular(CalculateIpcaTlBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }

        List<IPCA_Tl> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());
        Long dias = DateUtils.businessDays(request.dateInit(), request.dateFim());
        BigDecimal fatorAcumulado = BigDecimal.ONE;

        for (var dado : listEntity){
            fatorAcumulado = fatorAcumulado.multiply(dado.getFator());
        }

        BigDecimal valorFinal = BigDecimal
                .valueOf(request.valor())
                .multiply(fatorAcumulado)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal percentualAcumulado = fatorAcumulado
                .subtract(BigDecimal.ONE)
                .divide(BigDecimal.valueOf(100))
                .setScale(6, BigDecimal.ROUND_HALF_UP);

        return new CalculateIpcaTlBetweenDateResponse(request.dateInit(), request.dateFim(), dias, valorFinal, percentualAcumulado);
    }
}
