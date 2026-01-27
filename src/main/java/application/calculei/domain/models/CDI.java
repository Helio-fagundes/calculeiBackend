package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_cdi")
public class CDI extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
