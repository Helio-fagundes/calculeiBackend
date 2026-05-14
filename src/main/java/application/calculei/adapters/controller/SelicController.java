package application.calculei.adapters.controller;

import application.calculei.usecase.selic.diario.CalculateSelicDiarioAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateRequest;
import application.calculei.usecase.selic.diario.dto.CalculateSelicDiarioBetweenDateResponse;
import application.calculei.usecase.selic.mensal.CalculateSelicMensalAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.mensal.dto.CalculateSelicMensalBetweenDateRequest;
import application.calculei.usecase.selic.mensal.dto.CalculateSelicMensalBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selic")
public class SelicController {

    private final CalculateSelicMensalAccumulatedValueBetweenDates useCaseCalculateDays;
    private final CalculateSelicDiarioAccumulatedValueBetweenDates  useCaseCalculateMonths;

    public SelicController(
            CalculateSelicMensalAccumulatedValueBetweenDates useCaseCalculateDays,
            CalculateSelicDiarioAccumulatedValueBetweenDates useCaseCalculateMonths
    ) {
        this.useCaseCalculateDays = useCaseCalculateDays;
        this.useCaseCalculateMonths = useCaseCalculateMonths;
    }

    @PostMapping("/mensal/calculate/between-dates")
    public CalculateSelicMensalBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateSelicMensalBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }

    @PostMapping("/diario/calculate/between-dates")
    public CalculateSelicDiarioBetweenDateResponse calculateDiarioBetweenDate(@Valid @RequestBody CalculateSelicDiarioBetweenDateRequest request){
        return useCaseCalculateMonths.execute(request);
    }
}
