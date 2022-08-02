package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.regioncusco.llankasun.server.commons.ParamsManager;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.dtos.ResidenteBodyDto;
import pe.regioncusco.llankasun.server.models.entities.Residente;
import pe.regioncusco.llankasun.server.services.ResidenteService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@Rest
@RequestMapping(ResidenteWebController.PERSONAL)
public class ResidenteWebController {
    public static final String PERSONAL = "/v1/residente";

    private static final String LISTAR = "/listar";
    private static final String BUSCAR = "/buscar/{termino}";
    private static final String CONSULTAR = "/consultar/{ndocumento}";
    private static final String MOSTRAR = "/mostrar/{ndocumento}";
    private static final String CREAR = "/crear";
    private static final String EDITAR = "/editar/{ndocumento}";
    private static final String ELIMINAR = "/eliminar/{ndocumento}";


    @Autowired
    private ResidenteService residenteService;
/*
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarResidenteTodos(){
        return residenteService.listarResidentesTodos();
    }
*/
    @GetMapping("/metas/{documento}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarMetasPorResidente(@PathVariable String documento){
        return residenteService.metasPorResidente(documento);
    }

    @GetMapping(LISTAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<Page<Residente>> findAll(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size") int size){
        return new ResponseEntity<>(residenteService.findAll(page,size), HttpStatus.OK);
    }

    @GetMapping(BUSCAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<Page<Residente>> findByNumeroDocumentoContains(@PathVariable String termino, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size") int size){
        return new ResponseEntity<>(residenteService.findByNumeroDocumentoContains(termino, page, size), HttpStatus.OK);
    }

    @GetMapping(MOSTRAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<Residente> find(@PathVariable String ndocumento){
        return new ResponseEntity<>(residenteService.find(ndocumento), HttpStatus.OK);
    }

    @GetMapping(CONSULTAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<Residente> consultar(@PathVariable String ndocumento){
        return new ResponseEntity<>(residenteService.consultar(ndocumento), HttpStatus.OK);
    }

    @PostMapping(CREAR)
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<Residente> create(@Valid @RequestBody ResidenteBodyDto residente){
        return new ResponseEntity<>(residenteService.create(residente), HttpStatus.CREATED);
    }

    @PutMapping(EDITAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<Residente> update(@PathVariable String ndocumento, @Valid @RequestBody ResidenteBodyDto residente){
        return new ResponseEntity<>(residenteService.update(ndocumento, residente), HttpStatus.OK);
    }

    @DeleteMapping(ELIMINAR)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public void delete(@PathVariable String ndocumento){
        residenteService.delete(ndocumento);
    }

}
