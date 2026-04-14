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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
