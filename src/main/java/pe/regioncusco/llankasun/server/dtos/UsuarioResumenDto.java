package pe.regioncusco.llankasun.server.dtos;

import java.util.Set;

public class UsuarioResumenDto {
    private String usuario;
    private String nombres;
    private Set<String> roles;

    public UsuarioResumenDto(String usuario, String nombres, Set<String> roles) {
        this.usuario = usuario;
        this.nombres = nombres;
        this.roles = roles;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
