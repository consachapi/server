package pe.regioncusco.llankasun.server.dtos;

public class MetaAsignacionDto {
    private String meta;
    private String cproyecto;
    private String proyecto;
    private boolean asignacion;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getCproyecto() {
        return cproyecto;
    }

    public void setCproyecto(String cproyecto) {
        this.cproyecto = cproyecto;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public boolean isAsignacion() {
        return asignacion;
    }

    public void setAsignacion(boolean asignacion) {
        this.asignacion = asignacion;
    }

}
