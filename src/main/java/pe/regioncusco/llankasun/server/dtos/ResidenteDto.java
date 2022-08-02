package pe.regioncusco.llankasun.server.dtos;

import pe.regioncusco.llankasun.server.models.entities.Cargo;
import pe.regioncusco.llankasun.server.models.entities.Oficina;
import pe.regioncusco.llankasun.server.models.entities.Perfil;

public class ResidenteDto {
    private String numDocumento;
    private String nombres;
    private String apellidos;
    private Oficina oficina;
    private Cargo cargo;
    private Perfil perfil;
    private String email;
    private String telefono;
    private boolean enable;

    public ResidenteDto(){

    }

    public ResidenteDto(String numDocumento, String nombres, String apellidos, Oficina oficina, Cargo cargo, Perfil perfil, String email, String telefono, boolean enable) {
        this.numDocumento = numDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.oficina = oficina;
        this.cargo = cargo;
        this.perfil = perfil;
        this.email = email;
        this.telefono = telefono;
        this.enable = enable;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
