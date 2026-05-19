package application.calculei.domain.models;

import java.time.LocalDate;

public class HistoryPdfValueDomain {

    private Long id;
    private LocalDate date;
    private String json;
    private String token;

    public HistoryPdfValueDomain(Long id, LocalDate date, String json, String token) {
        this.id = id;
        this.date = date;
        this.json = json;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getJson() {
        return json;
    }

    public String getToken() {
        return token;
    }

}
