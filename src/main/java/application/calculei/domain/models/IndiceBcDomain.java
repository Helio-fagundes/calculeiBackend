package application.calculei.domain.models;

import java.time.LocalDate;

public class IndiceBcDomain {

    private Long id;
    private String serie;
    private LocalDate lastUpdate;
    private String description;
    private int codigo;
    private String periodicidade;
    private String urlBC;

    public IndiceBcDomain(Long id, String serie, LocalDate lastUpdate,  String description, int codigo, String periodicidade, String urlBC) {
        this.id = id;
        this.serie = serie;
        this.lastUpdate = lastUpdate;
        this.description = description;
        this.codigo = codigo;
        this.periodicidade = periodicidade;
        this.urlBC = urlBC;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public String getUrlBC() {
        return urlBC;
    }

    public void setUrlBC(String urlBC) {
        this.urlBC = urlBC;
    }
}
