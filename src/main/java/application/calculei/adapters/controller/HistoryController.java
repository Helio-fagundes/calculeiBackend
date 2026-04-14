package application.calculei.adapters.controller;

import application.calculei.usecase.history_pdf_value.HistoryPdfValueMethod;
import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueRequest;
import application.calculei.usecase.history_pdf_value.dto.HistoryPdfValueResponse;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("history")
public class HistoryController {

    private final HistoryPdfValueMethod usecase;


    public HistoryController(HistoryPdfValueMethod usecase) {
        this.usecase = usecase;
    }

    @PostMapping("/save")
    public void saveHistory(@Valid @RequestBody HistoryPdfValueRequest request) {
        usecase.save(request);
    }

    @GetMapping("/findbytoken")
    public JsonNode findByToken(@Valid @RequestParam String token) {
        return usecase.findByToken(token);
    }
}
