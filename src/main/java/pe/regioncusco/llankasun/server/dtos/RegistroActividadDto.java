package pe.regioncusco.llankasun.server.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class RegistroActividadDto {
    private Long id;
    private ActividadDto actividad;
    private String imagen;
    private String descripcion;
    private String longitud;
    private String latitud;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="America/Lima")
    private Date fecha;

    public RegistroActividadDto(Long id, ActividadDto actividad, String imagen, String descripcion, String longitud, String latitud, Date fecha) {
        this.id = id;
        this.actividad = actividad;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActividadDto getActividad() {
        return actividad;
    }

    public void setActividad(ActividadDto actividad) {
        this.actividad = actividad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
}
