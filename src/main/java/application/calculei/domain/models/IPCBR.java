package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_ipc_br")
public class IPCBR extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
