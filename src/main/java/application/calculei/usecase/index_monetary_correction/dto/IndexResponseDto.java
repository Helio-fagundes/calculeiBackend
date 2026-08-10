package application.calculei.usecase.index_monetary_correction.dto;

public class IndexResponseDto {

    private String index;
    private String description;

    public IndexResponseDto(String index, String description) {
        this.index = index;
        this.description = description;
    }

    public IndexResponseDto(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }
}
