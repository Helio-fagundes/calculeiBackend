package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_ipcae")
public class IPCAE extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
