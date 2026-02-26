package application.calculei.infraestructure.repository.indices_bc;

import application.calculei.infraestructure.entity.IndiceBC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndicesBcIndexRepository extends JpaRepository<IndiceBC, Long> {

    Optional<IndiceBC> findByDescricao(String codigo);

}
