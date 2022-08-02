package pe.regioncusco.llankasun.server.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class ActividadDetalleDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String inicio;
    private String fin;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="America/Lima")
    private Date fecha;
    private String meta;
    private int estado;
    private ResidenteDto residente;

    public ActividadDetalleDto(Long id, String titulo, String descripcion, String inicio, String fin, Date fecha, String meta, int estado, ResidenteDto residente) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.inicio = inicio;
        this.fin = fin;
        this.fecha = fecha;
        this.meta = meta;
        this.estado = estado;
        this.residente = residente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ResidenteDto getResidente() {
        return residente;
    }

    public void setResidente(ResidenteDto residente) {
        this.residente = residente;
    }
}
