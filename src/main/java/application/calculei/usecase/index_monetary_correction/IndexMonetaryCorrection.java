package application.calculei.usecase.index_monetary_correction;

import application.calculei.domain.enums.index_enum.InterestCorrection;
import application.calculei.domain.enums.index_enum.MonetaryCorrection;
import application.calculei.usecase.index_monetary_correction.dto.IndexResponseDto;

import java.util.Arrays;
import java.util.List;

public class IndexMonetaryCorrection {

    public List<IndexResponseDto> getAllMonetaryCorrections() {
        return Arrays.stream(MonetaryCorrection.values())
                .map(index -> new IndexResponseDto(index.name(), index.getLabel(), index.getDescription()))
                .toList();
    }

    public List<IndexResponseDto>  getAllInterestCorrections() {
        return Arrays.stream(InterestCorrection.values())
                .map(object -> new IndexResponseDto(object.name(), object.getLabel(), object.getDescription()))
                .toList();
    }

}
