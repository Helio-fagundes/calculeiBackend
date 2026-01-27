package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_salario")
public class Salario extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
