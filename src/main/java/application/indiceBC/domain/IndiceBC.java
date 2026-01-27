package application.indiceBC.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_indices_bc")
public class IndiceBC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serie;
    private int codigo;
    private String descricao;
    private String urlBC;
    private String controller;
    private String periodo;
    private Date lastUpdate;

}
