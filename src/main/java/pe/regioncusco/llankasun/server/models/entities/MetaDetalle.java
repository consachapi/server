package pe.regioncusco.llankasun.server.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meta")
public class MetaDetalle {
    @Id
    private String meta;

    @Column(name = "cact_ai_obra")
    private String codigoActividadObra;

    private String finalidad;

    @Column(name = "grupo_func")
    private String grupoFuncional;

    @Column(name = "cadena_funcional")
    private String cadenaFuncional;

    private String programa;

    @Column(name = "cprod_pry")
    private String codigoProductoProyecto;

    @Column(name = "cprograma")
    private String codigoPrograma;

    @Column(name = "cfinalidad")
    private String codigoFinalidad;

    @Column(name = "prod_pry")
    private String productoProyecto;

    @Column(name = "cfuncion")
    private String codigoFuncion;

    @Column(name = "cgrupo_func")
    private String codigoGrupoFuncional;

    @Column(name = "act_ai_obra")
    private String actividadObra;

    @Column(name = "cdivision_func")
    private String codigoDivisionFuncional;

    @Column(name = "division_func")
    private String divisionFuncional;

    private String funcion;

    private boolean estado;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getCodigoActividadObra() {
        return codigoActividadObra;
    }

    public void setCodigoActividadObra(String codigoActividadObra) {
        this.codigoActividadObra = codigoActividadObra;
    }

    public String getFinalidad() {
        return finalidad;
    }

    public void setFinalidad(String finalidad) {
        this.finalidad = finalidad;
    }

    public String getGrupoFuncional() {
        return grupoFuncional;
    }

    public void setGrupoFuncional(String grupoFuncional) {
        this.grupoFuncional = grupoFuncional;
    }

    public String getCadenaFuncional() {
        return cadenaFuncional;
    }

    public void setCadenaFuncional(String cadenaFuncional) {
        this.cadenaFuncional = cadenaFuncional;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getCodigoProductoProyecto() {
        return codigoProductoProyecto;
    }

    public void setCodigoProductoProyecto(String codigoProductoProyecto) {
        this.codigoProductoProyecto = codigoProductoProyecto;
    }

    public String getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(String codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public String getCodigoFinalidad() {
        return codigoFinalidad;
    }

    public void setCodigoFinalidad(String codigoFinalidad) {
        this.codigoFinalidad = codigoFinalidad;
    }

    public String getProductoProyecto() {
        return productoProyecto;
    }

    public void setProductoProyecto(String productoProyecto) {
        this.productoProyecto = productoProyecto;
    }

    public String getCodigoFuncion() {
        return codigoFuncion;
    }

    public void setCodigoFuncion(String codigoFuncion) {
        this.codigoFuncion = codigoFuncion;
    }

    public String getCodigoGrupoFuncional() {
        return codigoGrupoFuncional;
    }

    public void setCodigoGrupoFuncional(String codigoGrupoFuncional) {
        this.codigoGrupoFuncional = codigoGrupoFuncional;
    }

    public String getActividadObra() {
        return actividadObra;
    }

    public void setActividadObra(String actividadObra) {
        this.actividadObra = actividadObra;
    }

    public String getCodigoDivisionFuncional() {
        return codigoDivisionFuncional;
    }

    public void setCodigoDivisionFuncional(String codigoDivisionFuncional) {
        this.codigoDivisionFuncional = codigoDivisionFuncional;
    }

    public String getDivisionFuncional() {
        return divisionFuncional;
    }

    public void setDivisionFuncional(String divisionFuncional) {
        this.divisionFuncional = divisionFuncional;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
