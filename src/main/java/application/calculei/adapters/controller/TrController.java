package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.tr.CalculateTrAccumulatedValueBetweenDates;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateRequest;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateResponse;
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
@RequestMapping("/tr")
@Tag(name = "TR", description = "Endpoints relacionados à Taxa Referencial (TR)")
public class TrController {

    private final CalculateTrAccumulatedValueBetweenDates useCaseCalculateDays;

    public TrController(CalculateTrAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado da TR entre duas datas",
            description = "Calcula o valor acumulado da Taxa Referencial (TR) entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo da TR entre datas", implementation = CalculateTrBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo da TR entre datas", implementation = CalculateTrBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateTrBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateTrBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
