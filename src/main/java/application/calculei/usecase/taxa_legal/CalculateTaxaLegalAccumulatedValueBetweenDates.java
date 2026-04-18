package application.calculei.usecase.taxa_legal;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.infraestructure.entity.TaxaLegal;
import application.calculei.infraestructure.repository.taxa_legal.TaxaLegalIndexRepository;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateRequest;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateResponse;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculateTaxaLegalAccumulatedValueBetweenDates{

    private final TaxaLegalIndexRepository repository;

    public CalculateTaxaLegalAccumulatedValueBetweenDates(TaxaLegalIndexRepository repository) {
        this.repository = repository;
    }

    public CalculateTaxaLegalBetweenDateResponse calcular(CalculateTaxaLegalBetweenDateRequest request){
        if (request.dataFim().isBefore(request.dataInit())){
            throw new IllegalArgumentException("A data final deve ser posterior à data inicial.");
        }

        Long dias = DateUtils.businessDays(request.dataInit(), request.dataFim());
        List<TaxaLegal> listEntity = repository.findByDataInitBetween(request.dataInit(), request.dataFim());
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

        return new CalculateTaxaLegalBetweenDateResponse(request.dataInit(), request.dataFim(), dias, valorFinal, percentualAcumulado);
    }
}
