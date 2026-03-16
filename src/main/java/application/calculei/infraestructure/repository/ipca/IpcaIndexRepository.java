package application.calculei.infraestructure.repository.ipca;

import application.calculei.infraestructure.entity.IPCA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IpcaIndexRepository extends JpaRepository<IPCA, Long> {
    List<IPCA> findByDataInitBetween(LocalDate dataInit, LocalDate fim);
    List<IPCA> findByDataInitLessThanEqual(LocalDate dataInit);
    @Query("SELECT MAX(x.dataInit) FROM IPCA x")
    LocalDate findMaxDataInit();
    Boolean existsByDataInit(LocalDate dataInit);
}
