package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_selic")
public class Selic extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
