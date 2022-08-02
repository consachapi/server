package pe.regioncusco.llankasun.server.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "residente")
public class ResidenteMeta {
    @Id
    @Column(name = "rs_ndoc")
    private String numDocumento;

    @Column(name = "rs_nombres")
    private String nombres;

    @Column(name = "rs_apellidos")
    private String apellidos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rs_cargo")
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rs_pfid")
    private Perfil perfil;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rs_oficina")
    private Oficina oficina;

    @Column(name = "rs_st")
    private boolean enable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meta_residente",
            joinColumns = { @JoinColumn(name = "mr_ndoc", referencedColumnName = "rs_ndoc") },
            inverseJoinColumns = { @JoinColumn(name = "mr_meta", referencedColumnName = "meta") }
    )
    private List<Meta> metas;

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

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }
}
