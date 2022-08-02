package pe.regioncusco.llankasun.server.models.repositorys;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.regioncusco.llankasun.server.models.entities.Actividad;
import pe.regioncusco.llankasun.server.models.entities.Residente;

import java.util.Date;
import java.util.List;

@Repository
public interface ActividadRepository extends PagingAndSortingRepository<Actividad, Long> {
    @Query("select a from Actividad a where a.fecha = :fech and a.usuario = :user and a.meta = :secfun and a.estado <> 0")
    List<Actividad> findActividadByUsuario(@Param("fech") Date fech, @Param("user") Residente user, @Param("secfun") String secfun);

    @Query("select a from Actividad a group by a.fecha, a.meta")
    List<Actividad> findAllActidades();

    @Query("select a from Actividad a where a.meta = :meta group by a.fecha")
    List<Actividad> findActividadPorMeta(@Param("meta") String meta);

    List<Actividad> findAllByFechaAndMeta(Date fecha, String meta);

    @Query("select a from Actividad a order by a.meta")
    List<Actividad> findAllActividadesOrderByMeta();
}