package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.selic.diario.CalculateSelicDiarioAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateRequest;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateResponse;
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
@RequestMapping("/selic")
@Tag(name = "SELIC", description = "Endpoints relacionados à SELIC")
public class SelicController {

    private final CalculateSelicDiarioAccumulatedValueBetweenDates  useCaseCalculateDays;

    public SelicController(
            CalculateSelicDiarioAccumulatedValueBetweenDates useCaseCalculateDays
    ) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/diario/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado da SELIC Diário entre duas datas",
            description = "Calcula o valor acumulado da SELIC Diário entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo da SELIC Diário entre datas", implementation = CalculateSelicDiarioBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo da SELIC Diário entre datas", implementation = CalculateSelicDiarioBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateSelicDiarioBetweenDateResponse calculateDiarioBetweenDate(@Valid @RequestBody CalculateSelicDiarioBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
