package application.calculei.adapters.controller;

import application.calculei.usecase.selic.mensal.CalculateSelicMensalAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.mensal.dto.CalculateSelicMensalBetweenDateRequest;
import application.calculei.usecase.selic.mensal.dto.CalculateSelicMensalBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selic-mensal")
public class SelicMensalController {

    private final CalculateSelicMensalAccumulatedValueBetweenDates useCaseCalculateDays;

    public SelicMensalController(CalculateSelicMensalAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateSelicMensalBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateSelicMensalBetweenDateRequest request){
        return useCaseCalculateDays.calcular(request);
    }
}
