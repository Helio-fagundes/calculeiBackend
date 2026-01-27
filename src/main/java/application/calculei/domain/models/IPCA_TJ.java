package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_ipca_tl")
public class IPCA_TJ extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
