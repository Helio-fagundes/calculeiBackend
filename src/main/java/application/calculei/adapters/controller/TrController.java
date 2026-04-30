package application.calculei.adapters.controller;

import application.calculei.usecase.tr.CalculateTrAccumulatedValueBetweenDates;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateRequest;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tr")
public class TrController {

    private final CalculateTrAccumulatedValueBetweenDates useCaseCalculateDays;

    public TrController(CalculateTrAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateTrBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateTrBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
