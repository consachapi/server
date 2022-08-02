package pe.regioncusco.llankasun.server.dtos;

import pe.regioncusco.llankasun.server.models.entities.Oficina;

import java.util.List;

public class ResponsableLocalizacionDto {
    private String numDocumento;
    private String nombres;
    private String apellidos;
    private String cargo;
    private Oficina oficina;
    private List<ActividadRegistroDto> actividades;

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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public List<ActividadRegistroDto> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadRegistroDto> actividades) {
        this.actividades = actividades;
    }
}
