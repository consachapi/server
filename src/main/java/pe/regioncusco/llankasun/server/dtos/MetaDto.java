package pe.regioncusco.llankasun.server.dtos;

public class MetaDto {
    private String numero;
    private String programa;
    private String proyecto;
    private String actividad;
    private String funcion;
    private String divisionFuncional;
    private String grupoFuncional;
    private String descripcion;

    public MetaDto(String numero, String programa, String proyecto, String actividad, String funcion, String divisionFuncional, String grupoFuncional, String descripcion) {
        this.numero = numero;
        this.programa = programa;
        this.proyecto = proyecto;
        this.actividad = actividad;
        this.funcion = funcion;
        this.divisionFuncional = divisionFuncional;
        this.grupoFuncional = grupoFuncional;
        this.descripcion = descripcion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getDivisionFuncional() {
        return divisionFuncional;
    }

    public void setDivisionFuncional(String divisionFuncional) {
        this.divisionFuncional = divisionFuncional;
    }

    public String getGrupoFuncional() {
        return grupoFuncional;
    }

    public void setGrupoFuncional(String grupoFuncional) {
        this.grupoFuncional = grupoFuncional;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
