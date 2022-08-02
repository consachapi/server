package pe.regioncusco.llankasun.server.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.regioncusco.llankasun.server.models.entities.RegistroActividad;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class ActividadRegistroDto {
    private Long id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="America/Lima")
    private Date fecha;

    List<RegistroActividad> registroActividads;

    public ActividadRegistroDto() {
    }

    public ActividadRegistroDto(Long id, Date fecha, List<RegistroActividad> registroActividads) {
        this.id = id;
        this.fecha = fecha;
        this.registroActividads = registroActividads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<RegistroActividad> getRegistroActividads() {
        return registroActividads;
    }

    public void setRegistroActividads(List<RegistroActividad> registroActividads) {
        this.registroActividads = registroActividads;
    }
}
