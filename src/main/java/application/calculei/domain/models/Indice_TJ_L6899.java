package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tj_l6899")
public class Indice_TJ_L6899 extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
