package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.services.UsuarioService;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@Rest
@RequestMapping(UsuarioWebController.USUARIO)
public class UsuarioWebController {
    public static final String USUARIO = "/v1/usuario";
    private static final String USUARIO_IPHONE = "/acceso/{id}";

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/datos")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl getDatosUsuarioLogin(){
        return usuarioService.verUsuario();
    }

    @GetMapping("/resumen")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl getUsuarioResumen(){
        return usuarioService.verResumenUsuario();
    }

    @GetMapping(USUARIO_IPHONE)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public ResponseEntity<Tpl> userByOrigen(@PathVariable String id){
        return new ResponseEntity<>(usuarioService.userByOrigen(id), HttpStatus.OK);
    }

}
