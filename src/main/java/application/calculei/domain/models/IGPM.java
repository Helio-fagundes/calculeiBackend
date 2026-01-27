package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_igpm")
public class IGPM extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
