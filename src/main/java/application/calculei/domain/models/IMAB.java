package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_imab")
public class IMAB extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
