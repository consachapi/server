package pe.regioncusco.llankasun.server.services;

import pe.regioncusco.llankasun.server.dtos.ResponsableDiarioDto;
import pe.regioncusco.llankasun.server.models.entities.Meta;

import java.util.List;

public interface ReporteService {
    Meta findAllById(String id);

    List<ResponsableDiarioDto>  findAllResidenteByMeta(String meta, Integer anio, Integer mes);
    String  generateResidenteByMeta(String meta, Integer anio, Integer mes);
    List<ResponsableDiarioDto>  findAllReportByResidente(Long oficina, Integer anio, Integer mes);
}
