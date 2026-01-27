package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tr")
public class TR extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
