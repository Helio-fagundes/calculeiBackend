package application.calculei.infraestructure.repository;

import application.calculei.infraestructure.entity.BaseEntity;
import application.calculei.infraestructure.entity.CDI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CdiIndexRepository extends JpaRepository<CDI, Long> {
    List<CDI> findByValor(Double valor);
    List<CDI> findByDataInitBetween(Date inicio, Date fim);
    List<CDI> findByDataMax(Date data);
}
