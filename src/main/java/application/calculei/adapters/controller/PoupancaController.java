package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.poupanca_antiga.CalculatePoupAntigoAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_antiga.dto.CalculatePoupAntigoBetweenDateRequest;
import application.calculei.usecase.poupanca_antiga.dto.CalculatePoupAntigoBetweenDateResponse;
import application.calculei.usecase.poupanca_antiga_nova.CalculatePoupNovaAndAntigaAccumulatedValueByPeriod;
import application.calculei.usecase.poupanca_antiga_nova.dto.CalculateIndexPoupBetweenDateRequest;
import application.calculei.usecase.poupanca_antiga_nova.dto.CalculateIndexPoupBetweenDateResponse;
import application.calculei.usecase.poupanca_nova.CalculatePoupNovaAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateRequest;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateResponse;
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
@RequestMapping("/poupanca")
@Tag(name = "Poupança", description = "Endpoints relacionados à Poupança")
public class PoupancaController {

    private final CalculatePoupNovaAccumulatedValueBetweenDates useCaseCalculatePoupancaBetweenDates;
    private final CalculatePoupAntigoAccumulatedValueBetweenDates useCaseCalculatePoupancaAntigaBetweenDates;
    private final CalculatePoupNovaAndAntigaAccumulatedValueByPeriod  useCaseCalculatePoupancaAndAntigaBetweenDates;

    public PoupancaController(
            CalculatePoupNovaAccumulatedValueBetweenDates useCaseCalculatePoupancaBetweenDates,
            CalculatePoupAntigoAccumulatedValueBetweenDates useCaseCalculatePoupancaAntigaBetweenDates,
            CalculatePoupNovaAndAntigaAccumulatedValueByPeriod useCaseCalculatePoupancaAndAntigaBetweenDates
    ) {
        this.useCaseCalculatePoupancaBetweenDates = useCaseCalculatePoupancaBetweenDates;
        this.useCaseCalculatePoupancaAntigaBetweenDates = useCaseCalculatePoupancaAntigaBetweenDates;
        this.useCaseCalculatePoupancaAndAntigaBetweenDates = useCaseCalculatePoupancaAndAntigaBetweenDates;
    }

    @PostMapping("/nova/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado da Poupança Nova entre duas datas",
            description = "Calcula o valor acumulado da Poupança Nova entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo da Poupança Nova entre datas", implementation = CalculatePoupNovaBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo da Poupança Nova entre datas", implementation = CalculatePoupNovaBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculatePoupNovaBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculatePoupNovaBetweenDateRequest request){
        return useCaseCalculatePoupancaBetweenDates.execute(request);
    }

    @PostMapping("/antiga/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado da Poupança Antiga entre duas datas",
            description = "Calcula o valor acumulado da Poupança Antiga entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo da Poupança Antiga entre datas", implementation = CalculatePoupAntigoBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo da Poupança Antiga entre datas", implementation = CalculatePoupAntigoBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculatePoupAntigoBetweenDateResponse calculateBetweenDateAntiga(@Valid @RequestBody CalculatePoupAntigoBetweenDateRequest request){
        return useCaseCalculatePoupancaAntigaBetweenDates.execute(request);
    }

    @PostMapping("/antiga-nova/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado da Poupança Nova e Antiga entre duas datas",
            description = "Calcula o valor acumulado da Poupança Nova e Antiga entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo da Poupança Nova e Antiga entre datas", implementation = CalculateIndexPoupBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo da Poupança Nova e Antiga entre datas", implementation = CalculateIndexPoupBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateIndexPoupBetweenDateResponse  calculateBetweenDate(@Valid @RequestBody CalculateIndexPoupBetweenDateRequest request){
        return useCaseCalculatePoupancaAndAntigaBetweenDates.execute(request);
    }
}
