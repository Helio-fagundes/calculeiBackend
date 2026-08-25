package application.calculei.usecase.index_monetary_correction.dto;

public class IndexResponseDto {

    private String index;
    private String label;
    private String description;

    public IndexResponseDto(String index, String label, String description) {
        this.label = label;
        this.index = index;
        this.description = description;
    }

    public IndexResponseDto(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
