package pe.regioncusco.llankasun.server.services;

import pe.regioncusco.llankasun.server.commons.MyValue;
import pe.regioncusco.llankasun.server.models.entities.Oficina;

import java.util.List;

public interface OficinaService {
    List<MyValue> seleccionActive();
    List<MyValue> selection();
    Oficina findOficinaById(Long id);
    Oficina findByAbreviatura(String abreviatura);
}
