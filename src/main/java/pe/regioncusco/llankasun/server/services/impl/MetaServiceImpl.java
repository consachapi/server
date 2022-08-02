package pe.regioncusco.llankasun.server.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.regioncusco.llankasun.server.dtos.*;
import pe.regioncusco.llankasun.server.exceptions.NotFoundException;
import pe.regioncusco.llankasun.server.models.entities.*;
import pe.regioncusco.llankasun.server.models.repositorys.*;
import pe.regioncusco.llankasun.server.services.MetaService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MetaServiceImpl implements MetaService {
    private static final Logger LOG = LoggerFactory.getLogger(MetaServiceImpl.class);
    @Autowired private MetaRepository metaRepository;
    @Autowired private ActividadRepository actividadRepository;
    @Autowired private RegistroActividadRepository registroActividadRepository;
    @Autowired private MetaDetalleRepository metaDetalleRepository;
    @Autowired private ResidenteMetaRepository residenteMetaRepository;

    @Override
    public List<MetasActvidadDto> findAllMetasAndResidente() {
        return getMetas(new Date());
    }

    @Override
    public List<MetasActvidadDto> findAllMetasAndResidenteByFecha(String fecha) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = formatter.parse(fecha);
            return getMetas(date);
        } catch (ParseException e) {
            e.printStackTrace();
            LOG.error("Error al realizar parse de la fecha {}, {}", fecha, e.getLocalizedMessage());
            return new ArrayList<>();
        }

    }

    @Override
    public List<MetasLocalizacionDto> findAllLocationByMetaAndFecha(String meta, String fecha) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date fech = null;
        try {
            fech = formatter.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            LOG.error("Error al realizar parse de la fecha {}, {}", fecha, e.getLocalizedMessage());
            return new ArrayList<>();
        }
        LOG.info("Fecha consulta {}", fech);

        if(!meta.isEmpty()){
            Optional<Meta> searchMeta = metaRepository.findById(meta);
            if(!searchMeta.isPresent()){
                LOG.error("Meta {} no encontrado", meta);
                return new ArrayList<>();
            }
            Meta metaEncontrado = searchMeta.get();
            MetasLocalizacionDto metasActvidadDto = new MetasLocalizacionDto();
            metasActvidadDto.setMeta(metaEncontrado.getMeta());

            List<MetasLocalizacionDto> metasActvidadDtos = new ArrayList<>();
            List<ResponsableLocalizacionDto> responsableDtos = new ArrayList<>();
            List<Residente> residentes = metaEncontrado.getResidentes();
            for (Residente residente: residentes){
                ResponsableLocalizacionDto responsableDto = new ResponsableLocalizacionDto();
                responsableDto.setNumDocumento(residente.getNumDocumento());
                responsableDto.setNombres(residente.getNombres());
                responsableDto.setApellidos(residente.getApellidos());
                responsableDto.setCargo(residente.getCargo().getDescripcion());
                responsableDto.setOficina(residente.getOficina());

                List<Actividad> actividads = actividadRepository.findActividadByUsuario(fech, residente, metaEncontrado.getMeta());
                List<ActividadRegistroDto> actividadRegistroDtos = new ArrayList<>();
                for(Actividad actividad: actividads){
                    ActividadRegistroDto actividadRegistroDto = new ActividadRegistroDto();
                    actividadRegistroDto.setId(actividad.getId());
                    actividadRegistroDto.setFecha(actividad.getFecha());

                    List<RegistroActividad> registroActividads = registroActividadRepository.findAllByActividad(actividad);
                    actividadRegistroDto.setRegistroActividads(registroActividads);
                    actividadRegistroDtos.add(actividadRegistroDto);
                }
                responsableDto.setActividades(actividadRegistroDtos);
                responsableDtos.add(responsableDto);
            }
            metasActvidadDto.setResponsables(responsableDtos);
            metasActvidadDtos.add(metasActvidadDto);

            return metasActvidadDtos;
        }

        LOG.info("Meta vacio");

        List<Meta> metas = metaRepository.findAllByEstado(true);
        List<MetasLocalizacionDto> metasActvidadDtos = new ArrayList<>();
        for (Meta met: metas) {
            MetasLocalizacionDto metasActvidadDto = new MetasLocalizacionDto();
            metasActvidadDto.setMeta(met.getMeta());

            List<ResponsableLocalizacionDto> responsableDtos = new ArrayList<>();
            List<Residente> residentes = met.getResidentes();
            for (Residente residente: residentes){
                ResponsableLocalizacionDto responsableDto = new ResponsableLocalizacionDto();
                responsableDto.setNumDocumento(residente.getNumDocumento());
                responsableDto.setNombres(residente.getNombres());
                responsableDto.setApellidos(residente.getApellidos());
                responsableDto.setCargo(residente.getCargo().getDescripcion());
                responsableDto.setOficina(residente.getOficina());

                List<Actividad> actividads = actividadRepository.findActividadByUsuario(fech, residente, met.getMeta());
                List<ActividadRegistroDto> actividadRegistroDtos = new ArrayList<>();
                for(Actividad actividad: actividads){
                    ActividadRegistroDto actividadRegistroDto = new ActividadRegistroDto();
                    actividadRegistroDto.setId(actividad.getId());
                    actividadRegistroDto.setFecha(actividad.getFecha());

                    List<RegistroActividad> registroActividads = registroActividadRepository.findAllByActividad(actividad);
                    actividadRegistroDto.setRegistroActividads(registroActividads);
                    actividadRegistroDtos.add(actividadRegistroDto);
                }
                responsableDto.setActividades(actividadRegistroDtos);
                responsableDtos.add(responsableDto);
            }
            metasActvidadDto.setResponsables(responsableDtos);
            metasActvidadDtos.add(metasActvidadDto);
        }
        return metasActvidadDtos;
    }

    @Override
    public List<MetaSeleccionDto> seleccion() {
        List<Meta> metas = metaRepository.findAllByEstado(true);
        List<MetaSeleccionDto> seleccionDtos = metas.stream().map(meta -> new MetaSeleccionDto(meta.getMeta(), meta.getMeta() + " - " + meta.getFinalidad())).collect(Collectors.toList());
        return seleccionDtos;
    }

    @Override
    public Meta findMetaById(String id) {
        Optional<Meta> optional = metaRepository.findById(id);
        if(!optional.isPresent()){
            LOG.error("No existe meta con id {}", id);
            return null;
        }
        return optional.get();
    }

    @Override
    public MetaDetalle findMetaDetalleById(String id) {
        Optional<MetaDetalle> metaDetalle = metaDetalleRepository.findById(id);
        if(!metaDetalle.isPresent()){
            throw new NotFoundException("No existe meta buscado.");
        }
        return metaDetalle.get();
    }

    @Override
    public ResidenteMeta findResidenteMetaById(String ndocumento) {
        Optional<ResidenteMeta> optional = residenteMetaRepository.findById(ndocumento);
        if(!optional.isPresent()){
            LOG.error("Responsable {} no exite", ndocumento);
            throw new NotFoundException("No existe el responsable");
        }
        return optional.get();
    }

    @Override
    public List<MetaAsignacionDto> asignacionPorResidente(String ndocumento) {
        ResidenteMeta residenteMeta = findResidenteMetaById(ndocumento);
        List<Meta> metasPersona = residenteMeta.getMetas();
        List<Meta> metas = metaRepository.findAllByEstado(true);
        List<MetaAsignacionDto> metasAsignados = new ArrayList<>();
        for(Meta meta: metas){
            MetaAsignacionDto metaAsignacionDto = new MetaAsignacionDto();
            metaAsignacionDto.setMeta(meta.getMeta());
            metaAsignacionDto.setCproyecto(meta.getCodigoProductoProyecto());
            metaAsignacionDto.setProyecto(meta.getProductoProyecto());
            Meta buscarMeta = metasPersona.stream().filter(meta1 -> meta.getMeta().equals(meta1.getMeta())).findAny().orElse(null);
            if(buscarMeta != null){
                metaAsignacionDto.setAsignacion(true);
            } else {
                metaAsignacionDto.setAsignacion(false);
            }
            metasAsignados.add(metaAsignacionDto);
        }
        return metasAsignados;
    }

    @Override
    public List<MetaAsignacionDto> asignarMatas(String ndocumento, List<MetaAsignacionDto> metas) {
        ResidenteMeta residenteMeta = findResidenteMetaById(ndocumento);

        List<Meta> metasAsignadas = new ArrayList<>();
        for(MetaAsignacionDto meta: metas){
            Meta met = new Meta();
            met.setMeta(meta.getMeta());
            met.setCodigoProductoProyecto(meta.getCproyecto());
            met.setProductoProyecto(meta.getProyecto());
            metasAsignadas.add(met);
        }
        residenteMeta.setMetas(metasAsignadas);

        ResidenteMeta residenteMetaNuevo = residenteMetaRepository.save(residenteMeta);
        return asignacionPorResidente(residenteMetaNuevo.getNumDocumento());
    }

    @Override
    public List<MetaDetalle> findAll() {
        List<MetaDetalle> metaDetalles = metaDetalleRepository.findAll();
        return metaDetalles;
    }

    @Override
    public MetaDetalle enabled(String id) {
        return enabled(id, true);
    }

    @Override
    public MetaDetalle disabled(String id) {
        return enabled(id, false);
    }

    private MetaDetalle enabled(String id, boolean enabled){
        MetaDetalle metaDetalle = findById(id);
        metaDetalle.setEstado(enabled);
        return metaDetalleRepository.save(metaDetalle);
    }

    private List<MetasActvidadDto> getMetas(Date fecha){
        List<Meta> metas = metaRepository.findAllByEstado(true);
        List<MetasActvidadDto> metasActvidadDtos = new ArrayList<>();
        for (Meta meta: metas) {
            MetasActvidadDto metasActvidadDto = new MetasActvidadDto();
            metasActvidadDto.setMeta(meta.getMeta());
            metasActvidadDto.setFinalidad(meta.getFinalidad());

            List<ResponsableDto> responsableDtos = new ArrayList<>();
            List<Residente> residentes = meta.getResidentes();
            for (Residente residente: residentes){
                ResponsableDto responsableDto = new ResponsableDto();
                responsableDto.setNumDocumento(residente.getNumDocumento());
                responsableDto.setNombres(residente.getNombres());
                responsableDto.setApellidos(residente.getApellidos());
                responsableDto.setCargo(residente.getCargo().getDescripcion());

                List<Actividad> actividads = actividadRepository.findActividadByUsuario(fecha, residente, meta.getMeta());
                responsableDto.setActividades(actividads);

                responsableDtos.add(responsableDto);
            }
            metasActvidadDto.setResponsables(responsableDtos);

            metasActvidadDtos.add(metasActvidadDto);
        }
        return metasActvidadDtos;
    }

    private MetaDetalle findById(String id){
        Optional<MetaDetalle> metaDetalle = metaDetalleRepository.findById(id);
        if(!metaDetalle.isPresent()){
            throw new NotFoundException("No existe meta " + id);
        }
        return metaDetalle.get();
    }

}
