package application.calculei.adapters.controller;

import application.calculei.usecase.simple_interest.CalculateInterestByPeriod;
import application.calculei.usecase.simple_interest.CalculateSimpleInterest;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/simple-interest")
public class SimpleInterestController {

    private final CalculateSimpleInterest calculateSimpleInterest;
    private final CalculateInterestByPeriod calculateInterestByPeriod;

    public SimpleInterestController(
            CalculateSimpleInterest calculateSimpleInterest,
            CalculateInterestByPeriod calculateInterestByPeriod
    ) {
        this.calculateSimpleInterest = calculateSimpleInterest;
        this.calculateInterestByPeriod = calculateInterestByPeriod;
    }

    @PostMapping("/{interest}")
    public SimpleInterestDto execute(@RequestBody @Valid SimpleInterestDto request, @PathVariable BigDecimal interest) {
        return calculateSimpleInterest.execute(request, interest);
    }

    @PostMapping("/period")
    public SimpleInterestDto executePeriod(@RequestBody @Valid SimpleInterestDto request) {
        return calculateInterestByPeriod.execute(request);
    }
}
