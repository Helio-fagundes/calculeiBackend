package application.calculei.infraestructure.repository.indices_bc;

import application.calculei.domain.models.IndiceBcDomain;
import application.calculei.infraestructure.entity.IndiceBC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IndicesBcIndexRepository extends JpaRepository<IndiceBC, Long> {
    Optional<IndiceBC> findBySerie(String serie);

    @Modifying
    @Query("UPDATE IndiceBC i SET i.lastUpdate = :lastUpdate WHERE i.serie = :serie")
    void updateLastUpdate(@Param("serie") String serie, @Param("lastUpdate") LocalDate lastUpdate);
}
