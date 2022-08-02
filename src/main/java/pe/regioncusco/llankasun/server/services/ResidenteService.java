package pe.regioncusco.llankasun.server.services;

import org.springframework.data.domain.Page;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.dtos.ResidenteBodyDto;
import pe.regioncusco.llankasun.server.models.entities.Residente;

public interface ResidenteService {
    Tpl listarResidentesTodos();
    Tpl metasPorResidente(String documento);
    Page<Residente> findAll(int page, int size);
    Page<Residente> findByNumeroDocumentoContains(String termino, int page, int size);
    Residente find(String ndocumento);
    Residente create(ResidenteBodyDto residente);
    Residente update(String ndocumento, ResidenteBodyDto residente);
    void delete(String ndocumento);
    Residente consultar(String ndocumento);
}
