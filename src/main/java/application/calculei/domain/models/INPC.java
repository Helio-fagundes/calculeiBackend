package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_inpc")
public class INPC extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
