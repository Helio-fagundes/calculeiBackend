package application.calculei.adapters.controller;

import application.calculei.usecase.cdi.CalculateAccumulatedValueBetweenDates;
import application.calculei.usecase.cdi.dto.CalculateBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cdi")
public class CdiController {

    private final CalculateAccumulatedValueBetweenDates useCaseCalculateDays;

    public CdiController(CalculateAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateBetweenDateRequest request){
        return useCaseCalculateDays.calcular(request);
    }
}
