package application.calculei.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_taxa_legal")
public class TaxaLegal extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
