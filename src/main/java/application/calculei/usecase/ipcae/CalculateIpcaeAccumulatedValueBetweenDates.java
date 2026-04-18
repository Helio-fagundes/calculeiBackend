package application.calculei.usecase.ipcae;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.IPCAE;
import application.calculei.infraestructure.repository.ipca_e.IpcaeIndexRepository;
import application.calculei.usecase.ipcae.dto.CalculateIpcaeBetweenDateRequest;
import application.calculei.usecase.ipcae.dto.CalculateIpcaeBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateIpcaeAccumulatedValueBetweenDates {

    private final IpcaeIndexRepository repository;

    public CalculateIpcaeAccumulatedValueBetweenDates(IpcaeIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIpcaeBetweenDateResponse calcular(CalculateIpcaeBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("a data inicio não pode ser superior a data final.");
        }

        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long dias = DateUtils.businessDays(request.dateInit(), request.dateFim());
        List<IPCAE> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());

        for (var entity : listEntity){
            fatorAcumulado = fatorAcumulado.multiply(entity.getFator());
        }
        BigDecimal valorFinal =
                BigDecimal.valueOf(request.valor())
                .multiply(fatorAcumulado)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal percentualAcumulado = fatorAcumulado
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(6, RoundingMode.HALF_UP);

        return new CalculateIpcaeBetweenDateResponse(request.dateInit(), request.dateFim(), dias, valorFinal, percentualAcumulado);
    }
}
