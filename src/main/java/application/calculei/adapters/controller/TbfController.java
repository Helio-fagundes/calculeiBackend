package application.calculei.adapters.controller;

import application.calculei.usecase.tbf.CalculateTbfAccumulatedValueBetweenDates;
import application.calculei.usecase.tbf.dto.CalculateTbfBetweenDateRequest;
import application.calculei.usecase.tbf.dto.CalculateTbfBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tbf")
public class TbfController {

    private final CalculateTbfAccumulatedValueBetweenDates useCaseCalculateDays;

    public TbfController(CalculateTbfAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
     }

     @PostMapping("/calculate/between-dates")
    public CalculateTbfBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateTbfBetweenDateRequest request){
         return useCaseCalculateDays.calcular(request);
     }
}
