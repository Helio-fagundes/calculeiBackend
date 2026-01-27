package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_ufir_rj")
public class UfirRJ extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
