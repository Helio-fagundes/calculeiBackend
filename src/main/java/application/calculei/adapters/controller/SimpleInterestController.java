package application.calculei.adapters.controller;

import application.calculei.usecase.simple_interest.CalculateSimpleInterestSix;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/simple-interest")
public class SimpleInterestController {

    private final CalculateSimpleInterestSix calculateSimpleInterestSix;

    public SimpleInterestController(
            CalculateSimpleInterestSix calculateSimpleInterestSix
    ) {
        this.calculateSimpleInterestSix = calculateSimpleInterestSix;
    }

    @PostMapping("/{interest}")
    public SimpleInterestDto execute(@RequestBody @Valid SimpleInterestDto request, @PathVariable BigDecimal interest) {
        return calculateSimpleInterestSix.execute(request, interest);
    }
}
