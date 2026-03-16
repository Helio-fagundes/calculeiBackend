package application.calculei.adapters.controller;

import application.calculei.usecase.selic.diario.CalculateSelicDiarioAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateRequest;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selic-diario")
public class SelicDiarioController {

    private final CalculateSelicDiarioAccumulatedValueBetweenDates useCaseCalculateDays;

    public SelicDiarioController(CalculateSelicDiarioAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateSelicDiarioBetweenDateResponse calculateSelicDiarioBetweenDateResponse(@Valid @RequestBody CalculateSelicDiarioBetweenDateRequest request){
        return useCaseCalculateDays.calcular(request);
    }
}
