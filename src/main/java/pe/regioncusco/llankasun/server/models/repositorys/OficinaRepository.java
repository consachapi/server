package pe.regioncusco.llankasun.server.models.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.regioncusco.llankasun.server.models.entities.Oficina;

import java.util.Optional;

public interface OficinaRepository extends JpaRepository<Oficina, Long> {
    Optional<Oficina> findByAbreviatura(String abreviatura);
}
