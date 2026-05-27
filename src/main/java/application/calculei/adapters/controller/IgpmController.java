package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.igpm.CalculateIgpmAccumulatedValueBetweenDates;
import application.calculei.usecase.igpm.dto.CalculateIgpmBetweenDateRequest;
import application.calculei.usecase.igpm.dto.CalculateIgpmBetweenDateResponse;
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
@RequestMapping("/igpm")
@Tag(name = "IGP-M", description = "Endpoints relacionados ao IGP-M")
public class IgpmController {

    private final CalculateIgpmAccumulatedValueBetweenDates useCaseCalculateDays;

    public IgpmController(CalculateIgpmAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado do IGP-M entre duas datas",
            description = "Calcula o valor acumulado do IGP-M entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do IGP-M entre datas", implementation = CalculateIgpmBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo do IGP-M entre datas", implementation = CalculateIgpmBetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateIgpmBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateIgpmBetweenDateRequest request) {
        return useCaseCalculateDays.execute(request);
    }
}
