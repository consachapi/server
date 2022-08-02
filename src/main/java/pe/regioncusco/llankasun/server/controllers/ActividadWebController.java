package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.services.ActividadService;

import javax.annotation.security.RolesAllowed;

@Rest
@RequestMapping("/v1/actividad")
public class ActividadWebController {
    @Autowired
    private ActividadService actividadService;

    @GetMapping("/eventos")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarEventos(){
        return actividadService.listarEventosTodos();
    }

    @GetMapping("/eventos/meta/{meta}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarEventosPorMetas(@PathVariable String meta){
        return actividadService.listarEventosPorMeta(meta);
    }

    @GetMapping("/listar/meta/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarActividadesPorMetaFecha(@PathVariable Long id){
        return actividadService.listarActividadesPorFecha(id);
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarTodoActividades(){
        return actividadService.listarTodoActividades();
    }

    @GetMapping("/listar/imagenes/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarImagenes(@PathVariable Long id){
        return actividadService.getImages(id);
    }

    @GetMapping("/listar/imagenes")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl getImagesTodo(){
        return actividadService.getImagesTodo();
    }

    @GetMapping("/registro/ver/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl verImagenRegistro(@PathVariable Long id){
        return actividadService.verRegistro(id);
    }

}
