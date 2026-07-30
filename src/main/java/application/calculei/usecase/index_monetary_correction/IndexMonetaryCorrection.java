package application.calculei.usecase.index_monetary_correction;

import application.calculei.domain.index_enum.InterestCorrection;
import application.calculei.domain.index_enum.MonetaryCorrection;

import java.util.List;

public class IndexMonetaryCorrection {

    public List<MonetaryCorrection> getAllMonetaryCorrections() {
        return List.of(MonetaryCorrection.values());
    }

    public List<InterestCorrection>  getAllInterestCorrections() {
        return List.of(InterestCorrection.values());
    }
}
