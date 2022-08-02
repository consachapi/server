package pe.regioncusco.llankasun.server.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.regioncusco.llankasun.server.commons.MyValue;
import pe.regioncusco.llankasun.server.exceptions.NotFoundException;
import pe.regioncusco.llankasun.server.models.entities.Cargo;
import pe.regioncusco.llankasun.server.models.repositorys.CargoRepository;
import pe.regioncusco.llankasun.server.services.CargoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CargoServiceImpl implements CargoService {
    @Autowired private CargoRepository cargoRepository;

    @Override
    public List<MyValue> selection() {
        List<Cargo> cargos = cargoRepository.findAll();
        return cargos.stream().map(cargo -> new MyValue(cargo.getId(), cargo.getDescripcion())).collect(Collectors.toList());
    }

    @Override
    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    @Override
    public Cargo create(String cargo) {
        Cargo carg = new Cargo();
        carg.setDescripcion(cargo);
        return cargoRepository.save(carg);
    }

    @Override
    public Cargo update(Long id, String cargo) {
        Optional<Cargo> optional = cargoRepository.findById(id);
        if(!optional.isPresent()){
            throw new NotFoundException("No existe cargo");
        }
        Cargo carg = optional.get();
        carg.setDescripcion(cargo);
        return cargoRepository.save(carg);
    }

    @Override
    public void delete(Long id) {
        Optional<Cargo> optional = cargoRepository.findById(id);
        if(!optional.isPresent()){
            throw new NotFoundException("No existe cargo");
        }
        cargoRepository.delete(optional.get());
    }
}
