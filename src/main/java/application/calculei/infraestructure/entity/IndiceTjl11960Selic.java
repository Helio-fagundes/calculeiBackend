package application.calculei.infraestructure.entity;

import application.calculei.infraestructure.entity.mapped_class.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_tj_l11960_selic")
public class IndiceTjl11960Selic extends BaseEntity {

    @JoinColumn(name = "ID_BC", referencedColumnName = "id_bc", nullable = false)
    @ManyToOne
    private IndiceBC indiceBC;

    public IndiceTjl11960Selic(Long id, BigDecimal fator, LocalDate dataInit, IndiceBC indiceBC) {
        super(id, fator, dataInit);
        this.indiceBC = indiceBC;
    }
}
