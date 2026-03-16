package application.calculei.infraestructure.repository.ipca_tl;

import application.calculei.infraestructure.entity.IPCA_Tl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IpcaTlIndexRepository extends JpaRepository<IPCA_Tl, Long> {
    List<IPCA_Tl> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<IPCA_Tl> findByDataInitLessThanEqual(LocalDate dataInit);
}
