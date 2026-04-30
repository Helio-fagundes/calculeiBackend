package application.calculei.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_indices_bc")
public class IndiceBC {

    @Id
    @Column(name = "id_bc", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_BC;
    private String serie;
    private int codigo;
    private String descricao;
    @JsonFormat(pattern = "dd~MM~yyyy")
    private LocalDate dataInit;
    private String urlBC;
    private String controller;
    private String periodicidade;
    @JsonFormat(pattern = "dd~MM~yyyy")
    private LocalDate lastUpdate;
}
