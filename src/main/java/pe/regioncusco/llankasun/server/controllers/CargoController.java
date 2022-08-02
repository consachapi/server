package pe.regioncusco.llankasun.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.regioncusco.llankasun.server.commons.MyValue;
import pe.regioncusco.llankasun.server.commons.ParamsManager;
import pe.regioncusco.llankasun.server.config.Rest;
import pe.regioncusco.llankasun.server.models.entities.Cargo;
import pe.regioncusco.llankasun.server.services.CargoService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Rest
@RequestMapping(CargoController.CARGO)
public class CargoController {
    public static final String CARGO = "/v1/cargo";
    private static final String SELECCIONAR = "/seleccionar";
    private static final String LISTAR = "/listar";
    private static final String CREAR = "/crear";
    private static final String EDITAR = "/editar/{id}";
    private static final String ELIMINAR = "/eliminar/{id}";

    @Autowired
    private CargoService cargoService;

    @GetMapping(SELECCIONAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<MyValue>> selection(){
        return new ResponseEntity<>(cargoService.selection(), HttpStatus.OK);
    }

    @GetMapping(LISTAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<List<Cargo>> findAll(){
        return new ResponseEntity<>(cargoService.findAll(), HttpStatus.OK);
    }

    @PostMapping(CREAR)
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<Cargo> create(@RequestBody String cargo){
        return new ResponseEntity<>(cargoService.create(cargo), HttpStatus.CREATED);
    }

    @PutMapping(EDITAR)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public ResponseEntity<Cargo> update(@PathVariable Long id, @RequestBody String cargo){
        return new ResponseEntity<>(cargoService.update(id, cargo), HttpStatus.OK);
    }

    @DeleteMapping(ELIMINAR)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RolesAllowed(ParamsManager.ROLE_ADMIN)
    public void update(@PathVariable Long id){
        cargoService.delete(id);
    }

}
