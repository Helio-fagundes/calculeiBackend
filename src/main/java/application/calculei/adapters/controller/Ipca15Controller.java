package application.calculei.adapters.controller;

import application.calculei.usecase.ipca15.CalculateIpca15AccumulatedValueBetweenDates;
import application.calculei.usecase.ipca15.dto.CalculateIpca15BetweenDateRequest;
import application.calculei.usecase.ipca15.dto.CalculateIpca15BetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ipca15")
public class Ipca15Controller {

    private final CalculateIpca15AccumulatedValueBetweenDates useCaseCalculateDays;

    public Ipca15Controller(CalculateIpca15AccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateIpca15BetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateIpca15BetweenDateRequest request){
        return useCaseCalculateDays.calcular(request);
    }
}
