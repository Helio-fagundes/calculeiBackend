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
@Table(name = "tbl_poupanca_antiga")
public class PoupAntiga extends BaseEntity{

    @JsonFormat(pattern = "dd~MM~yyyy")
    private Date datafim;

    @JoinColumn(name = "ID_BC", referencedColumnName = "id_bc", nullable = false)
    @ManyToOne
    private IndiceBC indiceBC;
}
