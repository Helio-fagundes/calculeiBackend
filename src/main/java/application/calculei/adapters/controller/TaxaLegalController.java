package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.taxa_legal.CalculateTaxaLegalAccumulatedValueBetweenDates;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateRequest;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("taxalegal")
@Tag(name = "Taxa Legal", description = "Endpoints relacionados à Taxa Legal")
public class TaxaLegalController {

    private final CalculateTaxaLegalAccumulatedValueBetweenDates taxaLegalAccumulatedValueBetweenDates;

    public TaxaLegalController(CalculateTaxaLegalAccumulatedValueBetweenDates taxaLegalAccumulatedValueBetweenDates) {
        this.taxaLegalAccumulatedValueBetweenDates = taxaLegalAccumulatedValueBetweenDates;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado da Taxa Legal entre duas datas",
            description = "Calcula o valor acumulado da Taxa Legal entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo da Taxa Legal entre datas", implementation = CalculateTaxaLegalBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo da Taxa Legal entre datas", implementation = CalculateTaxaLegalBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateTaxaLegalBetweenDateResponse calculateTaxaLegalBetweenDateResponse(@RequestBody @Valid CalculateTaxaLegalBetweenDateRequest request){
        return taxaLegalAccumulatedValueBetweenDates.execute(request);
    }
}
