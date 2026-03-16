package application.calculei.usecase.ipca;

import application.calculei.infraestructure.entity.IPCA;
import application.calculei.infraestructure.repository.ipca.IpcaIndexRepository;
import application.calculei.usecase.ipca.dto.CalculateIpcaBetweenDateRequest;
import application.calculei.usecase.ipca.dto.CalculateIpcaBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateIpcaAccumulatedValueBetweenDates {

    private final IpcaIndexRepository repository;

    public CalculateIpcaAccumulatedValueBetweenDates(IpcaIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIpcaBetweenDateResponse calcular(CalculateIpcaBetweenDateRequest request){

        if (request.dateFim().isBefore(request.dateFim())){
            throw new IllegalArgumentException("A data de início deve ser anterior à data de fim.");
        }

        BigDecimal fatorAcumulado = BigDecimal.ONE;
        List<IPCA> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());
        Long diasContados = ChronoUnit.DAYS.between(request.dateInit(), request.dateFim());

        for (IPCA entity : listEntity){
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

        return new CalculateIpcaBetweenDateResponse(request.dateInit(), request.dateFim(), diasContados, valorFinal, percentualAcumulado);
    }
}
