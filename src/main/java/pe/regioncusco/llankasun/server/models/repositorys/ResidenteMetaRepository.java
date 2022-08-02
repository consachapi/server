package pe.regioncusco.llankasun.server.models.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.regioncusco.llankasun.server.models.entities.Oficina;
import pe.regioncusco.llankasun.server.models.entities.Perfil;
import pe.regioncusco.llankasun.server.models.entities.ResidenteMeta;

import java.util.List;

public interface ResidenteMetaRepository extends JpaRepository<ResidenteMeta, String> {

    @Query(value = "select r from ResidenteMeta r where r.enable = :activo and r.perfil = :perfil order by r.nombres, r.apellidos")
    List<ResidenteMeta> findAllActive(boolean activo, Perfil perfil);

    @Query(value = "select r from ResidenteMeta r where r.enable = :activo and r.perfil = :perfil and r.oficina = :oficina order by r.nombres, r.apellidos")
    List<ResidenteMeta> findAllActiveByOficina(boolean activo, Perfil perfil, Oficina oficina);

    @Query(value = "select r from ResidenteMeta r where r.enable = :activo and r.perfil = :perfil group by r.oficina order by r.nombres, r.apellidos")
    List<ResidenteMeta> findAllByOficina(boolean activo, Perfil perfil);
}
