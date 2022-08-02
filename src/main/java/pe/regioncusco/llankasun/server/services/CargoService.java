package pe.regioncusco.llankasun.server.services;

import pe.regioncusco.llankasun.server.commons.MyValue;
import pe.regioncusco.llankasun.server.models.entities.Cargo;

import java.util.List;

public interface CargoService {
    List<MyValue> selection();
    List<Cargo> findAll();
    Cargo create(String cargo);
    Cargo update(Long id, String cargo);
    void delete(Long id);
}
