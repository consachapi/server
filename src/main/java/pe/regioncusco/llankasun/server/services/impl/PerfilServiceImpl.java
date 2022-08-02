package pe.regioncusco.llankasun.server.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.regioncusco.llankasun.server.commons.MyValue;
import pe.regioncusco.llankasun.server.models.entities.Perfil;
import pe.regioncusco.llankasun.server.models.repositorys.PerfilRepository;
import pe.regioncusco.llankasun.server.services.PerfilService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilServiceImpl implements PerfilService {
    @Autowired private PerfilRepository perfilRepository;

    @Override
    public List<MyValue> selection() {
        List<Perfil> perfils = perfilRepository.findAll();
        return perfils.stream().map(perfil -> new MyValue(perfil.getId(), perfil.getDescripcion())).collect(Collectors.toList());
    }
}
