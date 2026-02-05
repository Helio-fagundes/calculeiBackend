package application.calculei.infraestructure.entity.mappedClass;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double fator;
    private Double valor;
    @JsonFormat(pattern = "dd~MM~yyyy")
    private Date dataInit;

    protected BaseEntity(
            Long id,
            String nome,
            Double fator,
            Double valor,
            Date dataInit
    ) {
        this.id = id;
        this.nome = nome;
        this.fator = fator;
        this.valor = valor;
        this.dataInit = dataInit;
    }

}
