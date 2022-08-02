package pe.regioncusco.llankasun.server.dtos;

import java.util.List;
import java.util.Map;

public class ResponsableDiarioDto {
    private String ndocumento;
    private String nombres;
    private List<Map<String, Boolean>> diarios;

    public String getNdocumento() {
        return ndocumento;
    }

    public void setNdocumento(String ndocumento) {
        this.ndocumento = ndocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public List<Map<String, Boolean>> getDiarios() {
        return diarios;
    }

    public void setDiarios(List<Map<String, Boolean>> diarios) {
        this.diarios = diarios;
    }
}
