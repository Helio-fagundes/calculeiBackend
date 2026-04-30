package application.calculei.infraestructure.entity.mappedClass;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 20, scale = 10)
    private BigDecimal fator;
    @JsonFormat(pattern = "dd~MM~yyyy")
    private LocalDate dataInit;

    protected BaseEntity(
            Long id,
            BigDecimal fator,
            LocalDate dataInit
    ) {
        this.id = id;
        this.fator = fator;
        this.dataInit = dataInit;
    }

}
