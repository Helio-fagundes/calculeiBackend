package application.calculei.adapters.controller;

import application.calculei.usecase.igpdi.CalculateIgpdiAccumulatedValueBetweenDates;
import application.calculei.usecase.igpdi.dto.CalculateIgpdiBetweenDateRequest;
import application.calculei.usecase.igpdi.dto.CalculateIgpdiBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/igpdi")
public class IgpdiController {

    private final CalculateIgpdiAccumulatedValueBetweenDates useCaseCalculateDays;

    public IgpdiController(CalculateIgpdiAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateIgpdiBetweenDateResponse calculateBetweenDays(@Valid @RequestBody CalculateIgpdiBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
