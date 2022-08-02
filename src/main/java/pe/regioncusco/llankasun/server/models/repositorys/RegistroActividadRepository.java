package pe.regioncusco.llankasun.server.models.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pe.regioncusco.llankasun.server.models.entities.Actividad;
import pe.regioncusco.llankasun.server.models.entities.RegistroActividad;

import java.util.List;

public interface RegistroActividadRepository extends JpaRepository<RegistroActividad, Long> {
    List<RegistroActividad> findAllByActividad(Actividad actividad);
}
