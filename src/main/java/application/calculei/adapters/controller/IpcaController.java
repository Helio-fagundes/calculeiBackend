package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.ipca.CalculateIpcaAccumulatedValueBetweenDates;
import application.calculei.usecase.ipca.dto.CalculateIpcaBetweenDateRequest;
import application.calculei.usecase.ipca.dto.CalculateIpcaBetweenDateResponse;
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
@RequestMapping("/ipca")
@Tag(name = "IPCA", description = "Endpoints relacionados ao IPCA")
public class IpcaController {

    private final CalculateIpcaAccumulatedValueBetweenDates useCaseCalculateDays;

    public IpcaController(CalculateIpcaAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado do IPCA entre duas datas",
            description = "Calcula o valor acumulado do IPCA entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do IPCA entre datas", implementation = CalculateIpcaBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo do IPCA entre datas", implementation = CalculateIpcaBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateIpcaBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateIpcaBetweenDateRequest request) {
        return useCaseCalculateDays.execute(request);
    }
}
