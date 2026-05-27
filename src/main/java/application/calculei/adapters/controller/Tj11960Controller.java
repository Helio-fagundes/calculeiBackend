package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.tj_11960.CalculateTj11960SelicValueBetweenDates;
import application.calculei.usecase.tj_11960.dto.CalculateTj11960BetweenDateRequest;
import application.calculei.usecase.tj_11960.dto.CalculateTj11960BetweenDateResponse;
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
@RequestMapping("/tj11960")
@Tag(name = "TJ-11960", description = "Endpoints relacionados ao TJ-11960")
public class Tj11960Controller {

    private final CalculateTj11960SelicValueBetweenDates useCase;

    public Tj11960Controller(CalculateTj11960SelicValueBetweenDates useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/calculate/between-dates")
    @Operation(
            summary = "Calcular o valor acumulado do TJ-11960 entre duas datas",
            description = "Calcula o valor acumulado do TJ-11960 entre as datas fornecidas no request."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do TJ-11960 entre datas", implementation = CalculateTj11960BetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo do TJ-11960 entre datas", implementation = CalculateTj11960BetweenDateResponse.class)))
    @ApiPostResponses
    public CalculateTj11960BetweenDateResponse calculateBetweenDates(@Valid @RequestBody CalculateTj11960BetweenDateRequest request){
        return useCase.execute(request);
    }
}
