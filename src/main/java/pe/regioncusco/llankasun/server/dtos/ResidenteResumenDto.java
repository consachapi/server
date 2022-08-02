package pe.regioncusco.llankasun.server.dtos;

public class ResidenteResumenDto {
    private String nombres;
    private String apellidos;
    private String oficina;

    public ResidenteResumenDto() {
    }

    public ResidenteResumenDto(String nombres, String apellidos, String oficina) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.oficina = oficina;
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

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }
}
