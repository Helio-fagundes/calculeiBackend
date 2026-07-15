package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.tj_6899.CalculateTj6899UfirValueBetweenDates;
import application.calculei.usecase.tj_6899.dto.CalculateTj6899BetweenDateRequest;
import application.calculei.usecase.tj_6899.dto.CalculateTj6899BetweenDateResponse;
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
@RequestMapping("tj6899")
@Tag(name = "TJ-6899", description = "Endpoints relacionados ao TJ-6899")
public class Tj6899Controller {

    private final CalculateTj6899UfirValueBetweenDates useCaseCalculateDays;

    public Tj6899Controller(CalculateTj6899UfirValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado do TJ-6899 entre duas datas",
            description = "Calcula o valor acumulado do TJ-6899 entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do TJ-6899 entre datas", implementation = CalculateTj6899BetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo do TJ-6899 entre datas", implementation = CalculateTj6899BetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateTj6899BetweenDateResponse calculateTj6899BetweenDateResponse(@Valid @RequestBody CalculateTj6899BetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
