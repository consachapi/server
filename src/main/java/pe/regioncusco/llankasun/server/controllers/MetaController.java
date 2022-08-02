package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.regioncusco.llankasun.server.commons.ParamsManager;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.dtos.MetaAsignacionDto;
import pe.regioncusco.llankasun.server.dtos.MetaSeleccionDto;
import pe.regioncusco.llankasun.server.dtos.MetasActvidadDto;
import pe.regioncusco.llankasun.server.dtos.MetasLocalizacionDto;
import pe.regioncusco.llankasun.server.models.entities.MetaDetalle;
import pe.regioncusco.llankasun.server.models.entities.ResidenteMeta;
import pe.regioncusco.llankasun.server.services.MetaService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Rest
@RequestMapping(MetaController.META)
public class MetaController {
    public static final String META = "/v1/meta";
    private static final String LISTA = "/responsable/listar";
    private static final String LISTA_POR_FECHA = "/responsable/fecha/{fecha}";
    private static final String LOCALIZACION_POR_META_AND_FECHA = "/localizacion/meta";
    private static final String SELECCION = "/seleccion";
    private static final String BUSCAR = "/buscar/{id}";
    private static final String BUSCAR_RESIDENTE = "/buscar/residente/{id}";
    private static final String METAS_ASIGNADAS = "/asignacion/{id}";
    private static final String ASIGNAR_METAS = "/asignar/{id}";
    private static final String METAS_TODO = "/listar/todo";
    private static final String HABILITAR = "/habilitar/{id}";
    private static final String INHABILITAR = "/inhabilitar/{id}";

    @Autowired private MetaService metaService;

    @GetMapping(LISTA)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MetasActvidadDto>> findAllMetasAndResidente(){
        return new ResponseEntity<>(metaService.findAllMetasAndResidente(), HttpStatus.OK);
    }

    @GetMapping(LISTA_POR_FECHA)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MetasActvidadDto>> findAllMetasAndResidenteByFecha(@PathVariable String fecha){
        return new ResponseEntity<>(metaService.findAllMetasAndResidenteByFecha(fecha), HttpStatus.OK);
    }

    @GetMapping(LOCALIZACION_POR_META_AND_FECHA)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MetasLocalizacionDto>> findAllLocationByMetaAndFecha(@RequestParam("meta") String meta, @RequestParam("fecha") String fecha){
        return new ResponseEntity<>(metaService.findAllLocationByMetaAndFecha(meta, fecha), HttpStatus.OK);
    }

    @GetMapping(SELECCION)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MetaSeleccionDto>> seleccion(){
        return new ResponseEntity<>(metaService.seleccion(), HttpStatus.OK);
    }

    @GetMapping(BUSCAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<MetaDetalle> find(@PathVariable String id){
        return new ResponseEntity<>(metaService.findMetaDetalleById(id), HttpStatus.OK);
    }

    @GetMapping(BUSCAR_RESIDENTE)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<ResidenteMeta> findResidenteMetaById(@PathVariable String id){
        return new ResponseEntity<>(metaService.findResidenteMetaById(id), HttpStatus.OK);
    }

    @GetMapping(METAS_ASIGNADAS)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MetaAsignacionDto>> asignacionPorResidente(@PathVariable String id){
        return new ResponseEntity<>(metaService.asignacionPorResidente(id), HttpStatus.OK);
    }

    @PostMapping(ASIGNAR_METAS)
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MetaAsignacionDto>> asignacionPorResidente(@PathVariable String id, @RequestBody List<MetaAsignacionDto> metas){
        return new ResponseEntity<>(metaService.asignarMatas(id, metas), HttpStatus.CREATED);
    }

    @GetMapping(METAS_TODO)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MetaDetalle>> findAll(){
        return new ResponseEntity<>(metaService.findAll(), HttpStatus.OK);
    }

    @GetMapping(HABILITAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<MetaDetalle> enabled(@PathVariable String id){
        return new ResponseEntity<>(metaService.enabled(id), HttpStatus.OK);
    }

    @GetMapping(INHABILITAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<MetaDetalle> disabled(@PathVariable String id){
        return new ResponseEntity<>(metaService.disabled(id), HttpStatus.OK);
    }

}
