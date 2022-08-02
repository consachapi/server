package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.dtos.ActividadDto;
import pe.regioncusco.llankasun.server.dtos.RegistroActividadDto;
import pe.regioncusco.llankasun.server.services.ActividadService;

import javax.annotation.security.RolesAllowed;

@Rest
@RequestMapping("/actividad")
public class ActividadController {
    @Autowired
    private ActividadService actividadService;

    @GetMapping("/listar/usuario/{meta}/{fecha}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarActividadPorUsuario(@PathVariable String meta, @PathVariable String fecha){
        return actividadService.listarActividadPorUsuario(meta, fecha);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("llankasun_residente")
    public Tpl crearActividad(@RequestBody ActividadDto actividad){
        return actividadService.crearActividad(actividad);
    }

    @GetMapping("/ver/resumen/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl verActividadResumen(@PathVariable Long id){
        return actividadService.verActividadResumen(id);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("llankasun_residente")
    public Tpl editarActividad(@PathVariable Long id, @RequestBody ActividadDto actividad){
        return actividadService.editarActividad(id, actividad);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("llankasun_residente")
    public void eliminarActividad(@PathVariable Long id){
        actividadService.eliminarActividad(id);
    }

    @PostMapping("/registrar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("llankasun_residente")
    public Tpl registrarActividad(@PathVariable Long id, @RequestBody RegistroActividadDto registro){
        return actividadService.registrarActividad(id, registro);
    }

    @GetMapping("/listar/imagenes/{id}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarImagenes(@PathVariable Long id){
        return actividadService.getImages(id);
    }

    @PutMapping("/terminar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("llankasun_residente")
    public Tpl terminarActividad(@PathVariable Long id){
        return actividadService.terminarActividad(id);
    }

    @PutMapping("/editar/registro/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("llankasun_residente")
    public Tpl editarActividadRegistro(@PathVariable Long id, @RequestBody RegistroActividadDto registro){
        return actividadService.editarActividadRegistro(id, registro);
    }

    @DeleteMapping("/eliminar/registro/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed("llankasun_residente")
    public void eliminarRegistroActividad(@PathVariable Long id){
        actividadService.eliminarRegistroActividad(id);
    }

}
