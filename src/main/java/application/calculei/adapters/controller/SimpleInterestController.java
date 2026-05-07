package application.calculei.adapters.controller;

import application.calculei.usecase.simple_interest.CalculateSimpleInterest;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/simple-interest")
public class SimpleInterestController {

    private final CalculateSimpleInterest calculateSimpleInterest;

    public SimpleInterestController(
            CalculateSimpleInterest calculateSimpleInterest
    ) {
        this.calculateSimpleInterest = calculateSimpleInterest;
    }

    @PostMapping("/{interest}")
    public SimpleInterestDto execute(@RequestBody @Valid SimpleInterestDto request, @PathVariable BigDecimal interest) {
        return calculateSimpleInterest.execute(request, interest);
    }
}
