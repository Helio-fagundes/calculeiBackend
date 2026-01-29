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
@AllArgsConstructor
@Entity
@Table(name = "tbl_indices_bc")
public class IndiceBC {

    @Id
    @Column(name = "id_bc", nullable = false, columnDefinition = "Codigo do indice do banco central")
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
}
