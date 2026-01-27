package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tbf")
public class TBF extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
