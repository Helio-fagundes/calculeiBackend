package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiGetOneResponses;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.igpdi.CalculateIgpdiAccumulatedValueBetweenDates;
import application.calculei.usecase.igpdi.dto.CalculateIgpdiBetweenDateRequest;
import application.calculei.usecase.igpdi.dto.CalculateIgpdiBetweenDateResponse;
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
@RequestMapping("/igpdi")
@Tag(name = "IGP-DI", description = "Endpoints relacionados ao IGP-DI")
public class IgpdiController {

    private final CalculateIgpdiAccumulatedValueBetweenDates useCaseCalculateDays;

    public IgpdiController(CalculateIgpdiAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular valor acumulado do IGP-DI entre datas",
            description = "Calcula o valor acumulado do IGP-DI entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do IGP-DI entre datas", implementation = CalculateIgpdiBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo do IGP-DI entre datas", implementation = CalculateIgpdiBetweenDateResponse.class)))
    @ApiGetOneResponses
    public CalculateIgpdiBetweenDateResponse calculateBetweenDays(@Valid @RequestBody CalculateIgpdiBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
