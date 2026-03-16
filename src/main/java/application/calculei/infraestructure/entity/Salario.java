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
@Table(name = "tbl_salario")
public class Salario extends BaseEntity {

    @JoinColumn(name = "ID_BC", referencedColumnName = "id_bc", nullable = false)
    @ManyToOne
    private IndiceBC indiceBC;

        public Salario(Long id, BigDecimal fator, LocalDate dataInit, IndiceBC indiceBC) {
            super(id, fator, dataInit);
            this.indiceBC = indiceBC;
        }
}
