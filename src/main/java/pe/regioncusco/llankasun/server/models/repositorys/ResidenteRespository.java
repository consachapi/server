package pe.regioncusco.llankasun.server.models.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pe.regioncusco.llankasun.server.models.entities.Residente;

import java.util.List;

@Repository
public interface ResidenteRespository extends PagingAndSortingRepository<Residente, String> {
    List<Residente> findAllByEnable(boolean enable);

    @Query(value = "select r from Residente r where r.perfil.id = 2")
    List<Residente> findAllByReponsable();

    Page<Residente> findAllByNumDocumentoContains(String termino, Pageable pageable);
}
