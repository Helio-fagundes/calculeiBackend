package application.calculei.domain.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public Index() {

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

    public static Index calculatePercentual(LocalDate date, String value) {
        BigDecimal percentual = new BigDecimal(value.replace(",", "."));
        BigDecimal factor = percentual
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .add(BigDecimal.ONE);

        return new Index(null, factor, date);
    }
}
