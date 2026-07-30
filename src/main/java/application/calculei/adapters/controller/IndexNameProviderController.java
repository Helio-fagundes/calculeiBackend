package application.calculei.adapters.controller;

import application.calculei.domain.index_enum.InterestCorrection;
import application.calculei.domain.index_enum.MonetaryCorrection;
import application.calculei.infraestructure.swagger.ApiGetAllResponses;
import application.calculei.usecase.index_monetary_correction.IndexMonetaryCorrection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index-name")
@Tag(name = "Nomes dos Índices", description = "Endpoints relacionados à obtenção de nomes de índices de correção monetária e juros")
public class IndexNameProviderController {

    private final IndexMonetaryCorrection indexMonetaryCorrection;

    public IndexNameProviderController(IndexMonetaryCorrection indexMonetaryCorrection) {
        this.indexMonetaryCorrection = indexMonetaryCorrection;
    }

    @GetMapping("/monetary-correction")
    @Operation(
            summary = "Obter nomes de índices de correção monetária",
            description = "Retorna uma lista de nomes de índices de correção monetária disponíveis para cálculo."
    )
    @ApiGetAllResponses
    public List<MonetaryCorrection> monetaryCorrection() {
        return indexMonetaryCorrection.getAllMonetaryCorrections();
    }

    @GetMapping("/interest-correction")
    @Operation(
            summary = "Obter nomes de índices de correção por juros",
            description = "Retorna uma lista de nomes de índices de correção por juros disponíveis para cálculo."
    )
    @ApiGetAllResponses
    public List<InterestCorrection> interestCorrection() {
        return indexMonetaryCorrection.getAllInterestCorrections();
    }
}
