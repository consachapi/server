package pe.regioncusco.llankasun.server.dtos;

import java.util.List;

public class MetasActvidadDto {
    private String meta;
    private String finalidad;
    List<ResponsableDto> responsables;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getFinalidad() {
        return finalidad;
    }

    public void setFinalidad(String finalidad) {
        this.finalidad = finalidad;
    }

    public List<ResponsableDto> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<ResponsableDto> responsables) {
        this.responsables = responsables;
    }
}
