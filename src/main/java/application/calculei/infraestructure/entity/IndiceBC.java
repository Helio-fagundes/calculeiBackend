package application.calculei.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_indices_bc")
public class IndiceBC {

    @Id
    @Column(name = "id_bc", nullable = false)
    private int ID_BC;
    private String serie;
    private int codigo;
    private String descricao;
    @JsonFormat(pattern = "dd~MM~yyyy")
    private Date dataInit;
    private String urlBC;
    private String controller;
    private String periodicidade;
    @JsonFormat(pattern = "dd~MM~yyyy")
    private Date lastUpdate;

    public IndiceBC(int ID_BC, String serie, int codigo, String descricao, Date dataInit, String urlBC, String controller, String periodicidade, Date lastUpdate) {
        this.ID_BC = ID_BC;
        this.serie = serie;
        this.codigo = codigo;
        this.descricao = descricao;
        this.dataInit = dataInit;
        this.urlBC = urlBC;
        this.controller = controller;
        this.periodicidade = periodicidade;
        this.lastUpdate = lastUpdate;
    }
}
