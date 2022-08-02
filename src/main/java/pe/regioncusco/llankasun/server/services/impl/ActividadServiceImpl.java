package pe.regioncusco.llankasun.server.services.impl;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.commons.Utils;
import pe.regioncusco.llankasun.server.config.AccessTokenImpl;
import pe.regioncusco.llankasun.server.dtos.ActividadDetalleDto;
import pe.regioncusco.llankasun.server.dtos.ActividadDto;
import pe.regioncusco.llankasun.server.dtos.RegistroActividadDto;
import pe.regioncusco.llankasun.server.dtos.ResidenteDto;
import pe.regioncusco.llankasun.server.models.entities.Actividad;
import pe.regioncusco.llankasun.server.models.entities.RegistroActividad;
import pe.regioncusco.llankasun.server.models.entities.Residente;
import pe.regioncusco.llankasun.server.models.repositorys.ActividadRepository;
import pe.regioncusco.llankasun.server.models.repositorys.RegistroActividadRepository;
import pe.regioncusco.llankasun.server.services.ActividadService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadServiceImpl implements ActividadService {
    private static final Logger LOG = LoggerFactory.getLogger(ActividadServiceImpl.class);

    @Autowired
    private AccessTokenImpl accessToken;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private RegistroActividadRepository registroActividadRepository;

    @Override
    public Tpl listarActividadPorUsuario(String meta, String fecha) {
        if(!verifyAccount()){
            return Utils.tpl(false, "anonymousUser");
        }

        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
            String usuario = accessToken.getUserId();
            Residente residente = new Residente();
            residente.setNumDocumento(usuario);
            List<Actividad> actividades = actividadRepository.findActividadByUsuario(date, residente, meta);
            List<ActividadDto> actividadDtoList = actividades.stream().map(actividad -> new ActividadDto(
                    actividad.getId(),
                    actividad.getTitulo(),
                    actividad.getDescripcion(),
                    actividad.getInicio(),
                    actividad.getFin(),
                    actividad.getFecha(),
                    actividad.getMeta(),
                    actividad.getEstado()
            )).collect(Collectors.toList());

            return Utils.tpl(true, actividadDtoList, actividadDtoList.size());
        } catch (ParseException e) {
            LOG.error("Error al convertir fecha, {}", e.getLocalizedMessage());
            return Utils.tpl(false, "Error al convertir fecha.");
        }
    }

    @Override
    public Tpl crearActividad(ActividadDto actividad) {
        if(!verifyAccount()){
            return Utils.tpl(false, "anonymousUser");
        }
        String usuario = accessToken.getUserId();
        Residente residente = new Residente();
        residente.setNumDocumento(usuario);

        Actividad activ = new Actividad();
        activ.setTitulo(actividad.getTitulo());
        activ.setDescripcion(actividad.getDescripcion());
        activ.setInicio(actividad.getInicio());
        activ.setFin(actividad.getFin());
        activ.setFecha(actividad.getFecha());
        activ.setMeta(actividad.getMeta());
        activ.setAnio(2021);
        activ.setFechaRegistro(new Date());
        activ.setUsuario(residente);
        activ.setEstado(1);
        return responseActividad(actividadRepository.save(activ));
    }

    @Override
    public Tpl verActividadResumen(Long id) {
        Actividad actividad = actividadRepository.findById(id).orElse(null);
        if(actividad == null){
            LOG.warn("No existe actividad con id {}.", id);
            return Utils.tpl(false, "No existe actividad.");
        }
        List<ActividadDto> actividadDto = new ArrayList<>();
        actividadDto.add(convert(actividad));
        return Utils.tpl(true, "", actividadDto, 1);
    }

    @Override
    public Tpl editarActividad(Long id, ActividadDto actividad) {
        Actividad activ = actividadRepository.findById(id).orElse(null);
        if(activ == null){
            LOG.warn("No existe actividad con id {}.", id);
            return Utils.tpl(false, "No existe actividad.");
        }
        String usuario = accessToken.getUserId();
        Residente residente = new Residente();
        residente.setNumDocumento(usuario);
        activ.setTitulo(actividad.getTitulo());
        activ.setDescripcion(actividad.getDescripcion());
        activ.setFechaRegistro(new Date());
        activ.setUsuario(residente);
        return responseActividad(actividadRepository.save(activ));
    }

    @Override
    public void eliminarActividad(Long id) {
        Actividad activ = actividadRepository.findById(id).orElse(null);
        if(activ == null){
            LOG.warn("No existe actividad con id {}.", id);
        }
        String usuario = accessToken.getUserId();
        Residente residente = new Residente();
        residente.setNumDocumento(usuario);
        activ.setFechaRegistro(new Date());
        activ.setUsuario(residente);
        activ.setEstado(0);
        actividadRepository.save(activ);
    }

    @Override
    public Tpl registrarActividad(Long id, RegistroActividadDto registro) {
        Actividad actividad = actividadRepository.findById(id).orElse(null);
        if(actividad == null){
            LOG.warn("No existe actividad con id {}.", id);
            return Utils.tpl(false, "No existe actividad.");
        }
        byte[] decoded = Utils.decodeFile(registro.getImagen());
        String imageName = Utils.generateNameFile();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("images/" + imageName + ".jpg");
            fos.write(decoded);
            fos.close();
        } catch (IOException e) {
            LOG.warn("No se ha podido guardar el imagen.");
            return Utils.tpl(false, "No se ha podido guardar el imagen.");
        }

        RegistroActividad registroActividad = new RegistroActividad();
        registroActividad.setActividad(actividad);
        registroActividad.setImagen(imageName);
        registroActividad.setDescripcion(registro.getDescripcion());
        registroActividad.setLongitud(registro.getLongitud());
        registroActividad.setLatitud(registro.getLatitud());
        registroActividad.setFechaRegistro(new Date());
        RegistroActividad regist = registroActividadRepository.save(registroActividad);
        if(regist == null){
            LOG.warn("No se puede guardar la informacion");
            return Utils.tpl(false, "No se puede guardar la informacion.");
        }
        List<RegistroActividadDto> registroActividadDtos = new ArrayList<>();
        registroActividadDtos.add(convertRegistroActividad(regist));
        return Utils.tpl(true, "Editado de forma correcta.", registroActividadDtos, 1);
    }

    @Override
    public Tpl getImages(Long id) {
        Actividad actividad = actividadRepository.findById(id).orElse(null);
        if(actividad == null){
            LOG.warn("No existe la actividad con id, {}", id);
            return Utils.tpl(false, "No existe la actividad.");
        }

        List<RegistroActividad> registroActividads = registroActividadRepository.findAllByActividad(actividad);
        List<RegistroActividadDto> regs = new ArrayList<>();
        for(RegistroActividad registroActividad: registroActividads){
            String imageName = registroActividad.getImagen() + ".jpg";
            File file = new File("images/" + imageName);
            FileInputStream imageInFile = null;
            try {
                imageInFile = new FileInputStream(file);
                byte imageData[] = new byte[(int) file.length()];
                imageInFile.read(imageData);
                String encoded = Base64.getEncoder().encodeToString(imageData);

                RegistroActividadDto registroActividadDto = new RegistroActividadDto(
                        registroActividad.getId(),
                        convert(registroActividad.getActividad()),
                        encoded,
                        registroActividad.getDescripcion(),
                        registroActividad.getLongitud(),
                        registroActividad.getLatitud(),
                        registroActividad.getFechaRegistro()
                );
                regs.add(registroActividadDto);
                imageInFile.close();
            } catch (FileNotFoundException e) {
                LOG.error("La imagen no existe, {}", e.getLocalizedMessage());
                return Utils.tpl(false, "No existe imagen " + imageName);
            } catch (IOException e) {
                LOG.error("Ocurrio un error al leer la imagen, {}", e.getLocalizedMessage());
                return Utils.tpl(false, "IOException");
            }
        }
        return Utils.tpl(true, "", regs, regs.size());
    }

    @Override
    public Tpl getImagesTodo() {
        List<RegistroActividad> registroActividads = registroActividadRepository.findAll(Sort.by(Sort.Direction.DESC, "actividad"));
        List<RegistroActividadDto> regs = new ArrayList<>();
        for(RegistroActividad registroActividad: registroActividads){
            String imageName = registroActividad.getImagen() + ".jpg";
            File file = new File("images/" + imageName);
            FileInputStream imageInFile = null;
            try {
                imageInFile = new FileInputStream(file);
                byte imageData[] = new byte[(int) file.length()];
                imageInFile.read(imageData);
                String encoded = Base64.getEncoder().encodeToString(imageData);

                RegistroActividadDto registroActividadDto = new RegistroActividadDto(
                        registroActividad.getId(),
                        convert(registroActividad.getActividad()),
                        encoded,
                        registroActividad.getDescripcion(),
                        registroActividad.getLongitud(),
                        registroActividad.getLatitud(),
                        registroActividad.getFechaRegistro()
                );
                regs.add(registroActividadDto);
                imageInFile.close();
            } catch (FileNotFoundException e) {
                LOG.error("La imagen no existe, {}", e.getLocalizedMessage());
                return Utils.tpl(false, "No existe imagen " + imageName);
            } catch (IOException e) {
                LOG.error("Ocurrio un error al leer la imagen, {}", e.getLocalizedMessage());
                return Utils.tpl(false, "IOException");
            }
        }
        return Utils.tpl(true, "", regs, regs.size());
    }

    @Override
    public Tpl terminarActividad(Long id) {
        Actividad actividad = actividadRepository.findById(id).orElse(null);
        if(actividad == null){
            LOG.warn("No existe la actividad con id, {}", id);
            return Utils.tpl(false, "No existe la actividad.");
        }
        Residente residente = new Residente();
        residente.setNumDocumento(accessToken.getUserId());
        actividad.setUsuario(residente);
        actividad.setFechaRegistro(new Date());
        actividad.setEstado(2);
        return responseActividad(actividadRepository.save(actividad));
    }

    @Override
    public void eliminarRegistroActividad(Long id) {
        RegistroActividad registroActividad = registroActividadRepository.findById(id).orElse(null);
        if(registroActividad == null){
            LOG.warn("No existe la imagen de la actividad con id, {}", id);
            return;
        }
        registroActividadRepository.delete(registroActividad);
    }

    @Override
    public Tpl editarActividadRegistro(Long id, RegistroActividadDto registro) {
        RegistroActividad registroActividad = registroActividadRepository.findById(id).orElse(null);
        if(registroActividad == null){
            LOG.warn("No existe la imagen de la actividad con id, {}", id);
            return Utils.tpl(false, "No existe la imagen");
        }
        byte[] decoded = Utils.decodeFile(registro.getImagen());
        String imageName = Utils.generateNameFile();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("images/" + imageName + ".jpg");
            fos.write(decoded);
            fos.close();
        } catch (IOException e) {
            LOG.warn("No se ha podido guardar el imagen.");
            return Utils.tpl(false, "No se ha podido guardar el imagen.");
        }

        registroActividad.setImagen(imageName);
        registroActividad.setDescripcion(registro.getDescripcion());
        registroActividad.setLongitud(registro.getLongitud());
        registroActividad.setLatitud(registro.getLatitud());
        registroActividad.setFechaRegistro(new Date());
        RegistroActividad regist = registroActividadRepository.save(registroActividad);
        if(regist == null){
            LOG.warn("No se puede guardar la informacion");
            return Utils.tpl(false, "No se puede guardar la informacion.");
        }
        List<RegistroActividadDto> registroActividadDtos = new ArrayList<>();
        registroActividadDtos.add(convertRegistroActividad(regist));
        return Utils.tpl(true, "Editado de forma correcta.", registroActividadDtos, 1);
    }

    @Override
    public Tpl listarEventosTodos() {
        List<Actividad> actividadList = actividadRepository.findAllActidades();
        List<ActividadDto> actividadDtos = actividadList.stream().map(actividad -> convert(actividad)).collect(Collectors.toList());
        return Utils.tpl(true, "", actividadDtos, actividadDtos.size());
    }

    @Override
    public Tpl listarEventosPorMeta(String meta) {
        List<Actividad> actividadList = actividadRepository.findActividadPorMeta(meta);
        List<ActividadDto> actividadDtos = actividadList.stream().map(actividad -> convert(actividad)).collect(Collectors.toList());
        return Utils.tpl(true, "", actividadDtos, actividadDtos.size());
    }

    @Override
    public Tpl listarActividadesPorFecha(Long id) {
        Actividad act = actividadRepository.findById(id).orElse(null);
        if(act == null){
            LOG.error("No se puede encontrar las actividades.");
            return Utils.tpl(false, "No se puede encontrar las actividades. Intente mas tarde.");
        }
        List<Actividad> actividades = actividadRepository.findAllByFechaAndMeta(act.getFecha(), act.getMeta());
        List<ActividadDetalleDto> actividadDtoList = actividades.stream().map(actividad -> convertDetaile(actividad)).collect(Collectors.toList());

        return Utils.tpl(true, actividadDtoList, actividadDtoList.size());
    }

    @Override
    public Tpl listarTodoActividades() {
        List<Actividad> actividadList = actividadRepository.findAllActividadesOrderByMeta();
        List<ActividadDetalleDto> actividadDtoList = actividadList.stream().map(actividad -> convertDetaile(actividad)).collect(Collectors.toList());
        return Utils.tpl(true, actividadDtoList, actividadDtoList.size());
    }

    @Override
    public Tpl verRegistro(Long id) {
        RegistroActividad registroActividad = registroActividadRepository.findById(id).orElse(null);
        if(registroActividad == null){
            LOG.error("No existe registro {}.", id);
            return Utils.tpl(false, "No existe registro.");
        }
        List<RegistroActividadDto> registroActividadDtoList = new ArrayList<>();
        String imageName = registroActividad.getImagen() + ".jpg";
        File file = new File("images/" + imageName);
        FileInputStream imageInFile = null;

        try {
            imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            String encoded = Base64.getEncoder().encodeToString(imageData);

            RegistroActividadDto registroActividadDto = new RegistroActividadDto(
                    registroActividad.getId(),
                    convert(registroActividad.getActividad()),
                    encoded,
                    registroActividad.getDescripcion(),
                    registroActividad.getLongitud(),
                    registroActividad.getLatitud(),
                    registroActividad.getFechaRegistro()
            );
            imageInFile.close();
            registroActividadDtoList.add(registroActividadDto);
            return Utils.tpl(true, "", registroActividadDtoList, registroActividadDtoList.size());
        } catch (FileNotFoundException e) {
            LOG.error("La imagen no existe, {}", e.getLocalizedMessage());
            return Utils.tpl(false, "No existe imagen " + imageName);
        } catch (IOException e) {
            LOG.error("Ocurrio un error al leer la imagen, {}", e.getLocalizedMessage());
            return Utils.tpl(false, "IOException");
        }
    }

    private Tpl responseActividad(Actividad actividad){
        Actividad updateActiv = actividadRepository.save(actividad);
        if(updateActiv == null){
            LOG.error("No se puede crear la actividad.");
            return Utils.tpl(false, "No se puede crear la actividad. Intente mas tarde.");
        }
        List<ActividadDto> actividadDtos = new ArrayList<>();
        actividadDtos.add(convert(updateActiv));
        return Utils.tpl(true, "Creado de forma correcta.", actividadDtos, 1);
    }

    private ActividadDto convert(Actividad actividad){
        return new ActividadDto(
                actividad.getId(),
                actividad.getTitulo(),
                actividad.getDescripcion(),
                actividad.getInicio(),
                actividad.getFin(),
                actividad.getFecha(),
                actividad.getMeta(),
                actividad.getEstado()
        );
    }

    private ActividadDetalleDto convertDetaile(Actividad actividad){
        return new ActividadDetalleDto(
                actividad.getId(),
                actividad.getTitulo(),
                actividad.getDescripcion(),
                actividad.getInicio(),
                actividad.getFin(),
                actividad.getFecha(),
                actividad.getMeta(),
                actividad.getEstado(),
                new ResidenteDto(
                        actividad.getUsuario().getNumDocumento(),
                        actividad.getUsuario().getNombres(),
                        actividad.getUsuario().getApellidos(),
                        actividad.getUsuario().getOficina(),
                        actividad.getUsuario().getCargo(),
                        actividad.getUsuario().getPerfil(),
                        actividad.getUsuario().getEmail(),
                        actividad.getUsuario().getTelefono(),
                        actividad.getUsuario().isEnable()
                )
        );
    }

    private RegistroActividadDto convertRegistroActividad(RegistroActividad registroActividad){
        return new RegistroActividadDto(
                registroActividad.getId(),
                new ActividadDto(
                        registroActividad.getActividad().getId(),
                        registroActividad.getActividad().getTitulo(),
                        registroActividad.getActividad().getDescripcion(),
                        registroActividad.getActividad().getInicio(),
                        registroActividad.getActividad().getFin(),
                        registroActividad.getActividad().getFecha(),
                        registroActividad.getActividad().getMeta(),
                        registroActividad.getActividad().getEstado()
                        ),
                registroActividad.getImagen(),
                registroActividad.getDescripcion(),
                registroActividad.getLongitud(),
                registroActividad.getLatitud(),
                registroActividad.getFechaRegistro()
        );
    }

    private boolean verifyAccount(){
        if(accessToken.getPrincipal().equals("anonymousUser")){
            LOG.warn("El usuario esta como anonymousUser ");
            return false;
        }
        return true;
    }
}
