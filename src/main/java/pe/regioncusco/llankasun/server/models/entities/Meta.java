package pe.regioncusco.llankasun.server.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "meta")
public class Meta {
    @Id
    private String meta;

    @Column(name = "cprod_pry")
    private String codigoProductoProyecto;

    @Column(name = "prod_pry")
    private String productoProyecto;

    private String finalidad;
    private boolean estado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "meta_residente",
            joinColumns = { @JoinColumn(name = "mr_meta", referencedColumnName = "meta") },
            inverseJoinColumns = { @JoinColumn(name = "mr_ndoc", referencedColumnName = "rs_ndoc") }
    )
    private List<Residente> residentes;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getCodigoProductoProyecto() {
        return codigoProductoProyecto;
    }

    public void setCodigoProductoProyecto(String codigoProductoProyecto) {
        this.codigoProductoProyecto = codigoProductoProyecto;
    }

    public String getProductoProyecto() {
        return productoProyecto;
    }

    public void setProductoProyecto(String productoProyecto) {
        this.productoProyecto = productoProyecto;
    }

    public String getFinalidad() {
        return finalidad;
    }

    public void setFinalidad(String finalidad) {
        this.finalidad = finalidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Residente> getResidentes() {
        return residentes;
    }

    public void setResidentes(List<Residente> residentes) {
        this.residentes = residentes;
    }
}
