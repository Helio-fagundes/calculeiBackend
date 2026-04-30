package application.calculei.adapters.controller;

import application.calculei.usecase.taxa_legal.CalculateTaxaLegalAccumulatedValueBetweenDates;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateRequest;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("taxalegal")
public class TaxaLegalController {

    private final CalculateTaxaLegalAccumulatedValueBetweenDates taxaLegalAccumulatedValueBetweenDates;

    public TaxaLegalController(CalculateTaxaLegalAccumulatedValueBetweenDates taxaLegalAccumulatedValueBetweenDates) {
        this.taxaLegalAccumulatedValueBetweenDates = taxaLegalAccumulatedValueBetweenDates;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateTaxaLegalBetweenDateResponse calculateTaxaLegalBetweenDateResponse(@RequestBody @Valid CalculateTaxaLegalBetweenDateRequest request){
        return taxaLegalAccumulatedValueBetweenDates.execute(request);
    }
}
