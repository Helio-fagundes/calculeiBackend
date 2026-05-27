package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.ipcae.CalculateIpcaeAccumulatedValueBetweenDates;
import application.calculei.usecase.ipcae.dto.CalculateIpcaeBetweenDateRequest;
import application.calculei.usecase.ipcae.dto.CalculateIpcaeBetweenDateResponse;
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
@RequestMapping("/ipcae")
@Tag(name = "IPCA-E", description = "Endpoints relacionados ao IPCA-E")
public class IpcaeController {

    private final CalculateIpcaeAccumulatedValueBetweenDates useCaseCalculateDays;

    public IpcaeController(CalculateIpcaeAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado do IPCA-E entre duas datas",
            description = "Calcula o valor acumulado do IPCA-E entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do IPCA-E entre datas", implementation = CalculateIpcaeBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo do IPCA-E entre datas", implementation = CalculateIpcaeBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateIpcaeBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateIpcaeBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
