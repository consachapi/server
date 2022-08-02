package pe.regioncusco.llankasun.server.dtos;

import pe.regioncusco.llankasun.server.models.entities.Cargo;
import pe.regioncusco.llankasun.server.models.entities.Perfil;

public class UsuarioDto {
    private String ndocumento;
    private String nombres;
    private Cargo perfil;
    private Perfil cargo;
    private String email;
    private String telefono;
    private boolean activo;

    public UsuarioDto(String ndocumento, String nombres, Cargo perfil, Perfil cargo, String email, String telefono, boolean activo) {
        this.ndocumento = ndocumento;
        this.nombres = nombres;
        this.perfil = perfil;
        this.cargo = cargo;
        this.email = email;
        this.telefono = telefono;
        this.activo = activo;
    }

    public String getNdocumento() {
        return ndocumento;
    }

    public void setNdocumento(String ndocumento) {
        this.ndocumento = ndocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Cargo getPerfil() {
        return perfil;
    }

    public void setPerfil(Cargo perfil) {
        this.perfil = perfil;
    }

    public Perfil getCargo() {
        return cargo;
    }

    public void setCargo(Perfil cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
