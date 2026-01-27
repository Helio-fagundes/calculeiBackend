package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_ipca15")
public class IPCA15 extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
