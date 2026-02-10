package application.calculei.infraestructure.repository.ipca_15;

import application.calculei.infraestructure.entity.IPCA15;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface Ipca15IndexRepository extends JpaRepository<IPCA15, Long> {
    List<IPCA15> findByValor(Double valor);
    List<IPCA15> findByDataInitBetween(Date inicio, Date fim);
    List<IPCA15> findByDataInitLessThanEqual(Date dataInit);
}
