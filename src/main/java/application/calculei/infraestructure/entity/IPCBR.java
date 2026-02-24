package application.calculei.infraestructure.entity;

import application.calculei.infraestructure.entity.mappedClass.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_ipc_br")
public class IPCBR extends BaseEntity {

    @JoinColumn(name = "ID_BC", referencedColumnName = "id_bc", nullable = false)
    @ManyToOne
    private IndiceBC indiceBC;

    public IPCBR(Long id, String nome, Double fator, Double valor, LocalDate dataInit, IndiceBC indiceBC) {
        super(id, nome, fator, valor, dataInit);
        this.indiceBC = indiceBC;
    }
}
