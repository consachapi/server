package pe.regioncusco.llankasun.server.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "residente")
public class Residente {
    @Id
    @Column(name = "rs_ndoc")
    private String numDocumento;

    @Column(name = "rs_nombres")
    private String nombres;

    @Column(name = "rs_apellidos")
    private String apellidos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rs_oficina")
    private Oficina oficina;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rs_cargo")
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rs_pfid")
    private Perfil perfil;

    @Column(name = "rs_email")
    private String email;

    @Column(name = "rs_telf")
    private String telefono;

    @Column(name = "rs_fechr")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/Lima")
    private Date fechaRegistro;

    @Column(name = "rs_usuario")
    private String usuario;

    @Column(name = "rs_st")
    private boolean enable;

    @Column(name = "rs_origen")
    private Integer origen;

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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getOrigen() {
        return origen;
    }

    public void setOrigen(Integer origen) {
        this.origen = origen;
    }
}
