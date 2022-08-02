package pe.regioncusco.llankasun.server.dtos;

import pe.regioncusco.llankasun.server.models.entities.Cargo;
import pe.regioncusco.llankasun.server.models.entities.Perfil;

public class ResidenteBodyDto {
    private String numDocumento;
    private Cargo cargo;
    private Perfil perfil;
    private boolean estado;

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
