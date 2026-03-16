package application.calculei.infraestructure.entity;

import application.calculei.infraestructure.entity.mappedClass.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_tj_l11960")
public class Indice_TJ_L11960 extends BaseEntity {

    @JoinColumn(name = "ID_BC", referencedColumnName = "id_bc", nullable = false)
    @ManyToOne
    private IndiceBC indiceBC;

        public Indice_TJ_L11960(Long id, BigDecimal fator, LocalDate dataInit, IndiceBC indiceBC) {
            super(id, fator, dataInit);
            this.indiceBC = indiceBC;
        }
}
