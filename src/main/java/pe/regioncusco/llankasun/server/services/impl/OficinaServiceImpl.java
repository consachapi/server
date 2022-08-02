package pe.regioncusco.llankasun.server.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pe.regioncusco.llankasun.server.commons.MyValue;
import pe.regioncusco.llankasun.server.exceptions.NotFoundException;
import pe.regioncusco.llankasun.server.models.entities.Oficina;
import pe.regioncusco.llankasun.server.models.entities.Perfil;
import pe.regioncusco.llankasun.server.models.entities.ResidenteMeta;
import pe.regioncusco.llankasun.server.models.repositorys.OficinaRepository;
import pe.regioncusco.llankasun.server.models.repositorys.ResidenteMetaRepository;
import pe.regioncusco.llankasun.server.services.OficinaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OficinaServiceImpl implements OficinaService {
    private static final Logger LOG = LoggerFactory.getLogger(OficinaServiceImpl.class);

    @Autowired private OficinaRepository oficinaRepository;
    @Autowired private ResidenteMetaRepository residenteMetaRepository;

    @Override
    public List<MyValue> seleccionActive() {
        Perfil perfil = new Perfil();
        perfil.setId(2L);
        List<ResidenteMeta> residenteMetas = residenteMetaRepository.findAllByOficina(true, perfil);
        LOG.info("Oficinas encontradas {}", residenteMetas.size());
        List<MyValue> myValues = new ArrayList<>();
        for (ResidenteMeta residenteMeta: residenteMetas){
            MyValue myValue = new MyValue();
            myValue.setValue(residenteMeta.getOficina().getId());
            myValue.setLabel(residenteMeta.getOficina().getDescripcion());

            myValues.add(myValue);
        }
        return myValues;
    }

    @Override
    public List<MyValue> selection() {
        List<Oficina> oficinas = oficinaRepository.findAll();
        return oficinas.stream().map(oficina -> new MyValue(oficina.getId(), oficina.getDescripcion())).collect(Collectors.toList());
    }

    @Override
    public Oficina findOficinaById(Long id) {
        return findById(id);
    }

    @Override
    public Oficina findByAbreviatura(String abreviatura) {
        Optional<Oficina> optionalOficina = oficinaRepository.findByAbreviatura(abreviatura);
        if(!optionalOficina.isPresent()){
            throw new NotFoundException("No existe oficina con abrev. " + abreviatura);
        }
        return optionalOficina.get();
    }

    private Oficina findById(Long id){
        Optional<Oficina> optionalOficina = oficinaRepository.findById(id);
        if(!optionalOficina.isPresent()){
            LOG.error("No existe oficina");
            throw new NotFoundException("No existe oficina");
        }
        return optionalOficina.get();
    }
}
