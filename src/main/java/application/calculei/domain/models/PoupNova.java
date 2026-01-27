package application.calculei.domain.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tbl_poupanca_nova")
public class PoupNova extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date datafim;
}
