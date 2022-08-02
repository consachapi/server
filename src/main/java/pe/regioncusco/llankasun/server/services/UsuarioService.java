package pe.regioncusco.llankasun.server.services;

import pe.regioncusco.llankasun.server.commons.Tpl;

import java.util.Map;

public interface UsuarioService {
    Tpl verDatosUsuario();
    Tpl listarMetasUsuario();
    Tpl verUsuario();
    Tpl verResumenUsuario();
    Tpl getMetasPorResidente(String documento);

    Tpl userByOrigen(String plataforma);
    void setEnabledAndPerfil(String username, boolean enabled, String perfil);
    void changePerfil(String username, String perfil);
    void removePerfil(String username, String perfil);
}
