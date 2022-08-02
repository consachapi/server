package pe.regioncusco.llankasun.server.services;

import pe.regioncusco.llankasun.server.dtos.MetaAsignacionDto;
import pe.regioncusco.llankasun.server.dtos.MetaSeleccionDto;
import pe.regioncusco.llankasun.server.dtos.MetasActvidadDto;
import pe.regioncusco.llankasun.server.dtos.MetasLocalizacionDto;
import pe.regioncusco.llankasun.server.models.entities.Meta;
import pe.regioncusco.llankasun.server.models.entities.MetaDetalle;
import pe.regioncusco.llankasun.server.models.entities.ResidenteMeta;

import java.util.List;

public interface MetaService {
    List<MetasActvidadDto> findAllMetasAndResidente();
    List<MetasActvidadDto> findAllMetasAndResidenteByFecha(String fecha);
    List<MetasLocalizacionDto> findAllLocationByMetaAndFecha(String meta, String fecha);
    List<MetaSeleccionDto> seleccion();
    Meta findMetaById(String id);
    MetaDetalle findMetaDetalleById(String id);
    ResidenteMeta findResidenteMetaById(String ndocumento);
    List<MetaAsignacionDto> asignacionPorResidente(String ndocumento);
    List<MetaAsignacionDto> asignarMatas(String ndocumento, List<MetaAsignacionDto> metas);
    List<MetaDetalle> findAll();
    MetaDetalle enabled(String id);
    MetaDetalle disabled(String id);
}
