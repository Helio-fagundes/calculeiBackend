package application.calculei.domain.models;

import java.util.Date;

public class Index {

    private Long id;
    private String nome;
    private Double fator;
    private Double valor;
    private Date dataInit;

    public Index(Long id, String nome, Double fator, Double valor, Date dataInit) {
        this.id = id;
        this.nome = nome;
        this.fator = fator;
        this.valor = valor;
        this.dataInit = dataInit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getFator() {
        return fator;
    }

    public void setFator(Double fator) {
        this.fator = fator;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataInit() {
        return dataInit;
    }

    public void setDataInit(Date dataInit) {
        this.dataInit = dataInit;
    }
}
