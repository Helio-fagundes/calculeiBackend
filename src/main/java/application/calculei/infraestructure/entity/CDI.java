package application.calculei.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_cdi")
public class CDI extends BaseEntity{

    @JoinColumn(name = "ID_BC", referencedColumnName = "id_bc", nullable = false)
    @ManyToOne
    private IndiceBC indiceBC;

    public CDI(Long id, String nome, Double fator, Double valor, Date dataInit, IndiceBC indiceBC) {
        super(id, nome, fator, valor, dataInit);
        this.indiceBC = indiceBC;
    }

}
