package application.calculei.infraestructure.repository.indice_tj_L11960;


import application.calculei.infraestructure.entity.Indice_TJ_L11960;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TjL11960IndexRepository extends JpaRepository<Indice_TJ_L11960, Long> {
    List<Indice_TJ_L11960> findByValor(Double valor);
    List<Indice_TJ_L11960> findByDataInitBetween(Date inicio, Date fim);
    List<Indice_TJ_L11960> findByDataInitLessThanEqual(Date dataInit);
}
