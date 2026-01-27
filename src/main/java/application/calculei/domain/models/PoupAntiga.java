package application.calculei.domain.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tbl_poupanca_antiga")
public class PoupAntiga extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date datafim;
}
