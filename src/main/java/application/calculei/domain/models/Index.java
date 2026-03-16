package application.calculei.domain.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Index {

    private Long id;
    private BigDecimal fator;
    private LocalDate dataInit;

    public Index(Long id, BigDecimal fator, LocalDate dataInit) {
        this.id = id;
        this.fator = fator;
        this.dataInit = dataInit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFator() {
        return fator;
    }

    public void setFator(BigDecimal fator) {
        this.fator = fator;
    }

    public LocalDate getDataInit() {
        return dataInit;
    }

    public void setDataInit(LocalDate dataInit) {
        this.dataInit = dataInit;
    }
}
