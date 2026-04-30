package application.calculei.adapters.controller;

import application.calculei.usecase.tj_6899.CalculateTj6899UfirValueBetweenDates;
import application.calculei.usecase.tj_6899.dto.CalculateTj6899BetweenDateRequest;
import application.calculei.usecase.tj_6899.dto.CalculateTj6899BetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tj6899")
public class Tj6899Controller {

    private final CalculateTj6899UfirValueBetweenDates useCaseCalculateDays;

    public Tj6899Controller(CalculateTj6899UfirValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateTj6899BetweenDateResponse calculateTj6899BetweenDateResponse(@Valid @RequestBody CalculateTj6899BetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
