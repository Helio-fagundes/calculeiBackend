package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.cdi.CalculateCdiAccumulatedValueBetweenDates;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cdi")
@Tag(name = "CDI", description = "Endpoints relacionados ao CDI")
public class CdiController {

    private final CalculateCdiAccumulatedValueBetweenDates useCaseCalculateDays;

    public CdiController(CalculateCdiAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular valor acumulado do CDI entre duas datas",
            description = "Recebe um intervalo de datas e retorna o valor acumulado do CDI nesse período."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do CDI entre datas", implementation = CalculateCdiBetweenDateRequest.class)))
    @ApiPostResponses
    public CalculateCdiBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateCdiBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
