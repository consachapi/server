package pe.regioncusco.llankasun.server.services;

import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.dtos.ActividadDto;
import pe.regioncusco.llankasun.server.dtos.RegistroActividadDto;

public interface ActividadService {
    Tpl listarActividadPorUsuario(String meta, String fecha);
    Tpl crearActividad(ActividadDto actividad);
    Tpl verActividadResumen(Long id);
    Tpl editarActividad(Long id, ActividadDto actividad);
    void eliminarActividad(Long id);
    Tpl registrarActividad(Long id, RegistroActividadDto registro);
    Tpl getImages(Long id);
    Tpl getImagesTodo();
    Tpl terminarActividad(Long id);
    void eliminarRegistroActividad(Long id);
    Tpl editarActividadRegistro(Long id, RegistroActividadDto registro);

    Tpl listarEventosTodos();
    Tpl listarEventosPorMeta(String meta);
    Tpl listarActividadesPorFecha(Long id);
    Tpl listarTodoActividades();
    Tpl verRegistro(Long id);
}
