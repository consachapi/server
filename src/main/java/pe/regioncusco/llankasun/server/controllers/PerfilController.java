package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import pe.regioncusco.llankasun.server.commons.MyValue;
import pe.regioncusco.llankasun.server.commons.ParamsManager;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.services.PerfilService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Rest
@RequestMapping(PerfilController.PERFIL)
public class PerfilController {
    public static final String PERFIL = "/v1/perfil";
    private static final String SELECCIONAR = "/seleccionar";

    @Autowired private PerfilService perfilService;

    @GetMapping(SELECCIONAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MyValue>> selection(){
        return new ResponseEntity<>(perfilService.selection(), HttpStatus.OK);
    }
}
