package pe.regioncusco.llankasun.server.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.commons.Utils;
import pe.regioncusco.llankasun.server.config.AccessTokenImpl;
import pe.regioncusco.llankasun.server.dtos.ResidenteBodyDto;
import pe.regioncusco.llankasun.server.dtos.ResidenteDto;
import pe.regioncusco.llankasun.server.exceptions.ConflictException;
import pe.regioncusco.llankasun.server.exceptions.NotFoundException;
import pe.regioncusco.llankasun.server.models.entities.Oficina;
import pe.regioncusco.llankasun.server.models.entities.Residente;
import pe.regioncusco.llankasun.server.models.repositorys.ResidenteRespository;
import pe.regioncusco.llankasun.server.restfull.PersonaFeignClient;
import pe.regioncusco.llankasun.server.restfull.models.PersonaData;
import pe.regioncusco.llankasun.server.restfull.models.PersonaTpl;
import pe.regioncusco.llankasun.server.services.OficinaService;
import pe.regioncusco.llankasun.server.services.ResidenteService;
import pe.regioncusco.llankasun.server.services.UsuarioService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResidenteServiceImpl implements ResidenteService {
    private static final Logger LOG = LoggerFactory.getLogger(ResidenteServiceImpl.class);

    @Autowired private AccessTokenImpl accessToken;
    @Autowired private ResidenteRespository residenteRespository;
    @Autowired private UsuarioService usuarioService;
    @Autowired private PersonaFeignClient personaFeignClient;
    @Autowired private OficinaService oficinaService;

    @Override
    public Tpl listarResidentesTodos() {
        LOG.info("Listando residentes activos...");
        List<Residente> residentes = residenteRespository.findAllByReponsable();
        List<ResidenteDto> residents = convertResidenteToDto(residentes);
        return Utils.tpl(true, residents, residents.size());
    }

    @Override
    public Tpl metasPorResidente(String documento) {
        return usuarioService.getMetasPorResidente(documento);
    }

    @Override
    public Page<Residente> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombres").and(Sort.by("numDocumento")));
        Page<Residente> residentePage = residenteRespository.findAll(pageable);
        LOG.info("Residentes/Responsables encontrados {}", residentePage.getSize());
        return residentePage;
    }

    @Override
    public Page<Residente> findByNumeroDocumentoContains(String termino, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombres").and(Sort.by("numDocumento")));
        Page<Residente> residentePage = residenteRespository.findAllByNumDocumentoContains(termino, pageable);
        LOG.info("Residentes/Responsables encontrados {}", residentePage.getSize());
        return residentePage;
    }

    @Override
    public Residente find(String ndocumento) {
        return findById(ndocumento);
    }

    @Override
    public Residente create(ResidenteBodyDto residente) {
        Residente resident = consultar(residente.getNumDocumento());
        resident.setCargo(residente.getCargo());
        resident.setPerfil(residente.getPerfil());
        resident.setOrigen(2);
        resident.setEnable(true);

        Optional<Residente> optionalResidente = residenteRespository.findById(resident.getNumDocumento());
        if(optionalResidente.isPresent()){
            throw new ConflictException("Persona con ndocumento " + resident.getNumDocumento() + ", ya existe");
        }
        resident.setFechaRegistro(new Date());
        resident.setUsuario(accessToken.getUserId());

        Residente nuevo = residenteRespository.save(resident);
        usuarioService.setEnabledAndPerfil(nuevo.getNumDocumento(), true, nuevo.getPerfil().getDescripcion());
        return nuevo;
    }

    @Override
    public Residente update(String ndocumento, ResidenteBodyDto residente) {
        Residente resid = findById(ndocumento);
        resid.setCargo(residente.getCargo());
        resid.setPerfil(residente.getPerfil());
        resid.setOrigen(2);
        resid.setEnable(residente.isEstado());

        resid.setFechaRegistro(new Date());
        resid.setUsuario(accessToken.getUserId());

        Residente update = residenteRespository.save(resid);
        usuarioService.changePerfil(update.getNumDocumento(), update.getPerfil().getDescripcion());
        return update;
    }

    @Override
    public void delete(String ndocumento) {
        Residente resid = findById(ndocumento);
        resid.setFechaRegistro(new Date());
        resid.setUsuario(accessToken.getUserId());
        resid.setEnable(false);
        Residente update = residenteRespository.save(resid);
        usuarioService.removePerfil(update.getNumDocumento(), update.getPerfil().getDescripcion());
    }

    @Override
    public Residente consultar(String ndocumento) {
        PersonaTpl personaTpl = personaFeignClient.getPersona(ndocumento);
        if(!personaTpl.isSuccess()){
            throw new NotFoundException("No existe persona con documento número " + ndocumento);
        }
        PersonaData personaData = personaTpl.getData().get(0);
        Oficina oficina = oficinaService.findByAbreviatura(personaData.getOficinaAbreviatura());
        Residente residente = new Residente();
        residente.setNumDocumento(personaData.getNumeroDocumento());
        residente.setNombres(personaData.getNombres());
        residente.setApellidos(personaData.getApellidos());
        residente.setOficina(oficina);
        residente.setEmail(personaData.getEmail());
        residente.setTelefono(personaData.getTelefono());
        return residente;
    }

    private List<ResidenteDto> convertResidenteToDto(List<Residente> residentes){
        List<ResidenteDto> residenteDtoList = residentes.stream().map(residente -> new ResidenteDto(
                residente.getNumDocumento(),
                residente.getNombres(),
                residente.getApellidos(),
                residente.getOficina(),
                residente.getCargo(),
                residente.getPerfil(),
                residente.getEmail(),
                residente.getTelefono(),
                residente.isEnable()
        )).collect(Collectors.toList());
        return residenteDtoList;
    }

    private Residente findById(String id){
        Optional<Residente> optionalResidente = residenteRespository.findById(id);
        if(!optionalResidente.isPresent()){
            LOG.error("No existe persona con documento {}", id);
            throw new NotFoundException("No existe persona con documento " + id);
        }
        return optionalResidente.get();
    }
}
