package application.calculei.infraestructure.repository.ipca_15;

import application.calculei.infraestructure.entity.IPCA15;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface Ipca15IndexRepository extends JpaRepository<IPCA15, Long> {
    List<IPCA15> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<IPCA15> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(x.dataInit) FROM IPCA15 x")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
