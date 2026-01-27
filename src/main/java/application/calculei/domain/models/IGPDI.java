package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_igp_di")
public class IGPDI extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
