package application.calculei.adapters.controller;

import application.calculei.infraestructure.swagger.ApiGetOneResponses;
import application.calculei.infraestructure.swagger.ApiPostResponses;
import application.calculei.infraestructure.swagger.ApiSaveResponse;
import application.calculei.usecase.history_pdf_value.HistoryPdfValueMethod;
import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueRequest;
import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueResponse;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@Tag(name = "Histórico de Cálculos", description = "Endpoints relacionados ao histórico de cálculos")
public class HistoryController {

    private final HistoryPdfValueMethod usecase;


    public HistoryController(HistoryPdfValueMethod usecase) {
        this.usecase = usecase;
    }

    @PostMapping("/save")
    @Operation(
            summary = "Salvar histórico de cálculo",
            description = "Salva o histórico de um cálculo realizado, incluindo os parâmetros e resultados."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Requisição para salvar histórico de cálculo", implementation = HistoryPdfValueRequest.class)))
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para salvar histórico de cálculo", implementation = HistoryPdfValueResponse.class)))
    @ApiSaveResponse
    public String saveHistory(@Valid @RequestBody HistoryPdfValueRequest request) {
        return "Token: " + usecase.save(request);
    }

    @GetMapping("/findbytoken")
    @Operation(
            summary = "Buscar histórico por token",
            description = "Busca o histórico de um cálculo utilizando um token único, retornando os detalhes do cálculo realizado."
    )
    @ApiResponse(content = @Content(schema = @Schema(description = "Resposta para buscar histórico por token", implementation = JsonNode.class)))
    @ApiGetOneResponses
    public JsonNode findByToken(@Valid @RequestParam String token) {
        return usecase.findByToken(token);
    }
}
