package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import pe.regioncusco.llankasun.server.commons.MyValue;
import pe.regioncusco.llankasun.server.commons.ParamsManager;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.services.OficinaService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Rest
@RequestMapping(OficinaController.OFICINA)
public class OficinaController {
    public static final String OFICINA = "/v1/oficina";
    private static final String SELECCION_ACTIVO = "/seleccion/activo";
    private static final String SELECCIONAR = "/seleccionar";

    @Autowired private OficinaService oficinaService;

    @GetMapping(SELECCION_ACTIVO)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MyValue>> selectionActive(){
        return new ResponseEntity<>(oficinaService.seleccionActive(), HttpStatus.OK);
    }

    @GetMapping(SELECCIONAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MyValue>> selection(){
        return new ResponseEntity<>(oficinaService.selection(), HttpStatus.OK);
    }

}
