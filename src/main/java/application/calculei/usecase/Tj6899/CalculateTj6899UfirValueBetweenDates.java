package application.calculei.usecase.Tj6899;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.Indice_TJ_L6899;
import application.calculei.infraestructure.repository.indice_tj_L6899.TjL6899IndexRepository;
import application.calculei.usecase.Tj6899.dto.CalculateTj6899BetweenDateRequest;
import application.calculei.usecase.Tj6899.dto.CalculateTj6899BetweenDateResponse;

import java.math.BigDecimal;
import java.util.List;

public class CalculateTj6899UfirValueBetweenDates {

    private final TjL6899IndexRepository repository;

    public CalculateTj6899UfirValueBetweenDates(TjL6899IndexRepository repository) {
        this.repository = repository;
    }

    public CalculateTj6899BetweenDateResponse calcular(CalculateTj6899BetweenDateRequest request){
        if (request.dataFinal().isBefore(request.dataInit())){
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }

        DateUtils  dateUtils = new DateUtils();
        int dias = dateUtils.businessDays(request.dataInit(), request.dataFinal());
        List<Indice_TJ_L6899> valorDataInicial = repository.findByDataInit(request.dataInit().withDayOfMonth(1));
        List<Indice_TJ_L6899> valorDataFinal = repository.findByDataInit(request.dataFinal().withDayOfMonth(1));

        if (!valorDataInicial.isEmpty() || !valorDataFinal.isEmpty()) {
            BigDecimal valorInicial = valorDataInicial.get(0).getFator();
            BigDecimal valorFinal = valorDataFinal.get(0).getFator();

            BigDecimal percentualAcumulado = valorInicial
                    .divide(valorFinal, 6, BigDecimal.ROUND_HALF_UP)
                    .setScale(6, BigDecimal.ROUND_HALF_UP);

            BigDecimal valorAcumulado = BigDecimal
                    .valueOf(request.valor())
                    .multiply(percentualAcumulado)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);

            return new CalculateTj6899BetweenDateResponse(request.dataInit(), request.dataFinal(), dias, valorAcumulado, percentualAcumulado);
        } else {
            throw new IllegalArgumentException("Não foram encontrados valores para as datas fornecidas.");
        }
    }
 }

