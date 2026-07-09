package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiGetAllResponses;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.ufir.UfirUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/ufir")
@Tag(name = "UFIR", description = "Endpoints relacionados à UFIR")
public class UfirController {

    private final UfirUseCase  ufirUseCase;

    public UfirController(UfirUseCase ufirUseCase) {
        this.ufirUseCase = ufirUseCase;
    }

    @GetMapping("/last-value")
    @Operation(
            summary = "Obter o último valor da UFIR",
            description = "Retorna o último valor disponível da Unidade Fiscal de Referência (UFIR)."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para cálculo da UFIR", implementation = CalculateCdiBetweenDateRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para cálculo da UFIR", implementation = CalculateCdiBetweenDateResponse.class)))
    @ApiGetAllResponses
    public BigDecimal getLastUfirValue() {
        return ufirUseCase.getLastUfirValue();
    }

    @PostMapping("/save/ufir")
    public void saveLastUfirValue(BigDecimal lastUfirValue, int year) {
        ufirUseCase.saveUfirValue(lastUfirValue, year);
    }
}
