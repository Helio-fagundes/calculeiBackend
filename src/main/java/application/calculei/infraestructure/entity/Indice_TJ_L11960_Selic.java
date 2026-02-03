package application.calculei.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_tj_l11960_selic")
public class Indice_TJ_L11960_Selic extends BaseEntity{

    @JoinColumn(name = "ID_BC", referencedColumnName = "id_bc", nullable = false)
    @ManyToOne
    private IndiceBC indiceBC;

}
