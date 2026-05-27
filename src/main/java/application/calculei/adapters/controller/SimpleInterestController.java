package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.simple_interest.CalculateInterestByPeriod;
import application.calculei.usecase.simple_interest.CalculateSimpleInterest;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/simple-interest")
@Tag(name = "Juros Simples", description = "Endpoints relacionados ao cálculo de juros simples")
public class SimpleInterestController {

    private final CalculateSimpleInterest calculateSimpleInterest;
    private final CalculateInterestByPeriod calculateInterestByPeriod;

    public SimpleInterestController(
            CalculateSimpleInterest calculateSimpleInterest,
            CalculateInterestByPeriod calculateInterestByPeriod
    ) {
        this.calculateSimpleInterest = calculateSimpleInterest;
        this.calculateInterestByPeriod = calculateInterestByPeriod;
    }

    @PostMapping("/{interest}")
    @Operation(
            summary = "Cálculo de Juros Simples sobre a quantidade de tempo fornecida no request.",
            description = "Cálculo de Juros Simples sobre a quantidade de tempo fornecida no request. O valor da taxa de juros deve ser fornecido como um parâmetro na URL."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do Juros Simples", implementation = SimpleInterestDto.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo do Juros Simples", implementation = SimpleInterestDto.class)))
    @ApiPostResponses
    public SimpleInterestDto execute(@RequestBody @Valid SimpleInterestDto request, @PathVariable BigDecimal interest) {
        return calculateSimpleInterest.execute(request, interest);
    }

    @PostMapping("/period")
    @Operation(
            summary = "Cálculo de Juros Simples sobre a quantidade de tempo fornecida no request.",
            description = "Cálculo de Juros Simples sobre a quantidade de tempo fornecida no request. O valor da taxa de juros é calculado com base no período informado."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo do Juros Simples por período", implementation = SimpleInterestDto.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo do Juros Simples por período", implementation = SimpleInterestDto.class)))
    @ApiPostResponses
    public SimpleInterestDto executePeriod(@RequestBody @Valid SimpleInterestDto request) {
        return calculateInterestByPeriod.execute(request);
    }
}
