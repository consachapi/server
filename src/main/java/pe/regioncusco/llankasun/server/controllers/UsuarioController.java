package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.services.UsuarioService;

import javax.annotation.security.RolesAllowed;

@Rest
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl verPerfilUsuario(){
        return usuarioService.verDatosUsuario();
    }

    @GetMapping("/listar/metas")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed("llankasun_residente")
    public Tpl listarMetasUsuario(){
        return usuarioService.listarMetasUsuario();
    }

}
