package pe.regioncusco.llankasun.server.models.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.regioncusco.llankasun.server.models.entities.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

}
