package application.calculei.adapters.controller;

import application.calculei.usecase.simple_interest.CalculateSimpleInterestSix;
import application.calculei.usecase.simple_interest.CalculateSimpleInterestTwelve;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple-interest")
public class SimpleInterestController {

    private final CalculateSimpleInterestSix calculateSimpleInterestSix;
    private final CalculateSimpleInterestTwelve calculateSimpleInterestTwelve;

    public SimpleInterestController(
            CalculateSimpleInterestSix calculateSimpleInterestSix,
            CalculateSimpleInterestTwelve calculateSimpleInterestTwelve
    ) {
        this.calculateSimpleInterestSix = calculateSimpleInterestSix;
        this.calculateSimpleInterestTwelve = calculateSimpleInterestTwelve;
    }

    @PostMapping("/6")
    public SimpleInterestDto executeSix(@RequestBody @Valid SimpleInterestDto request) {
        return calculateSimpleInterestSix.execute(request);
    }

    @PostMapping("/12")
    public SimpleInterestDto executeTwelve(@RequestBody @Valid SimpleInterestDto request) {
        return calculateSimpleInterestTwelve.execute(request);
    }
}
