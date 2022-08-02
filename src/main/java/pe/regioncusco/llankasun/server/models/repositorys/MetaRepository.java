package pe.regioncusco.llankasun.server.models.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.regioncusco.llankasun.server.models.entities.Meta;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta, String> {
    List<Meta> findAllByEstado(boolean estado);
    List<Meta> findAllByMetaAndEstado(String meta, boolean estado);
}
