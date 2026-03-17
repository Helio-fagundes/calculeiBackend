package application.calculei.adapters.controller;

import application.calculei.usecase.taxa_legal.CalculateTaxaLegalAccumulatedValueBetweenDates;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateRequest;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("taxalegal")
public class TaxaLegalController {

    private final CalculateTaxaLegalAccumulatedValueBetweenDates taxaLegalAccumulatedValueBetweenDates;

    public TaxaLegalController(CalculateTaxaLegalAccumulatedValueBetweenDates taxaLegalAccumulatedValueBetweenDates) {
        this.taxaLegalAccumulatedValueBetweenDates = taxaLegalAccumulatedValueBetweenDates;
    }

    public CalculateTaxaLegalBetweenDateResponse calculateTaxaLegalBetweenDateResponse(CalculateTaxaLegalBetweenDateRequest request){
        return taxaLegalAccumulatedValueBetweenDates.calcular(request);
    }
}
