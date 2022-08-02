package pe.regioncusco.llankasun.server.dtos;

import java.util.List;

public class MetasLocalizacionDto {
    private String meta;
    List<ResponsableLocalizacionDto> responsables;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public List<ResponsableLocalizacionDto> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<ResponsableLocalizacionDto> responsables) {
        this.responsables = responsables;
    }
}
