package application.calculei.domain.models;

import java.time.LocalDate;

public class IndiceBcDomain {

    private Long id;
    private String serie;
    private LocalDate lastUpdate;

    public IndiceBcDomain(Long id, String serie, LocalDate lastUpdate) {
        this.id = id;
        this.serie = serie;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
