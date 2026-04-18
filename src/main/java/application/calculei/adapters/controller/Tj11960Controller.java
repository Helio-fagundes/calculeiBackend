package application.calculei.adapters.controller;

import application.calculei.usecase.tj_11960.CalculateTj11960SelicValueBetweenDates;
import application.calculei.usecase.tj_11960.dto.CalculateTj11960BetweenDateRequest;
import application.calculei.usecase.tj_11960.dto.CalculateTj11960BetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tj11960")
public class Tj11960Controller {

    private final CalculateTj11960SelicValueBetweenDates useCase;

    public Tj11960Controller(CalculateTj11960SelicValueBetweenDates useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateTj11960BetweenDateResponse calculateBetweenDates(@Valid @RequestBody CalculateTj11960BetweenDateRequest request){
        return useCase.execute(request);
    }
}
