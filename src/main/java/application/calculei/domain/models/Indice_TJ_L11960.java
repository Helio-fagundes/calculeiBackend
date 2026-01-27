package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tj_l11960")
public class Indice_TJ_L11960 extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
