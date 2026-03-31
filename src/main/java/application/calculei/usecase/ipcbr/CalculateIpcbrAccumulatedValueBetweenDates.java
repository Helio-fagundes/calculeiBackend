package application.calculei.usecase.ipcbr;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.IPCBR;
import application.calculei.infraestructure.repository.ipc_br.IpcbrIndexRepository;
import application.calculei.usecase.ipcbr.dto.CalculateIpcbrBetweenDateRequest;
import application.calculei.usecase.ipcbr.dto.CalculateIpcbrBetweenDateResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateIpcbrAccumulatedValueBetweenDates {

    private final IpcbrIndexRepository repository;

    public CalculateIpcbrAccumulatedValueBetweenDates(IpcbrIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateIpcbrBetweenDateResponse calcular(CalculateIpcbrBetweenDateRequest request){
        if (request.dateFim().isBefore(request.dateInit())){
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }

        DateUtils  dateUtils = new DateUtils();
        BigDecimal fatorAcumulado = BigDecimal.ONE;
        Long dias = dateUtils.businessDays(request.dateInit(), request.dateFim());
        List<IPCBR> listEntity = repository.findByDataInitBetween(request.dateInit(), request.dateFim());

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
                .setScale(6, RoundingMode.HALF_UP);

        return new CalculateIpcbrBetweenDateResponse(request.dateInit(), request.dateFim(), dias, valorFinal, percentualAcumulado);
    }
}
