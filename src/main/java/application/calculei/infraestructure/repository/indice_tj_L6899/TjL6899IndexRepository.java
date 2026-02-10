package application.calculei.infraestructure.repository.indice_tj_L6899;

import application.calculei.infraestructure.entity.Indice_TJ_L6899;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TjL6899IndexRepository extends JpaRepository<Indice_TJ_L6899, Long> {
    List<Indice_TJ_L6899> findByValor(Double valor);
    List<Indice_TJ_L6899> findByDataInitBetween(Date inicio, Date fim);
    List<Indice_TJ_L6899> findByDataInitLessThanEqual(Date dataInit);
}
