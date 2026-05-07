package application.calculei.adapters.controller;

import application.calculei.domain.indexEnum.InterestCorrection;
import application.calculei.domain.indexEnum.MonetaryCorrection;
import application.calculei.usecase.index_monetary_correction.IndexMonetaryCorrection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index-name")
public class IndexNameProviderController {

    private final IndexMonetaryCorrection indexMonetaryCorrection;

    public IndexNameProviderController(IndexMonetaryCorrection indexMonetaryCorrection) {
        this.indexMonetaryCorrection = indexMonetaryCorrection;
    }

    @GetMapping("/monetary-correction")
    public List<MonetaryCorrection> monetaryCorrection() {
        return indexMonetaryCorrection.getAllMonetaryCorrections();
    }

    @GetMapping("/interest-correction")
    public List<InterestCorrection> interestCorrection() {
        return indexMonetaryCorrection.getAllInterestCorrections();
    }
}
