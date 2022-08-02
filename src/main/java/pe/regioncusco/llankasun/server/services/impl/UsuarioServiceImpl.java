package pe.regioncusco.llankasun.server.services.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.regioncusco.llankasun.server.commons.ParamsManager;
import pe.regioncusco.llankasun.server.commons.Tpl;
import pe.regioncusco.llankasun.server.commons.Utils;
import pe.regioncusco.llankasun.server.config.AccessTokenImpl;
import pe.regioncusco.llankasun.server.config.UtilConfig;
import pe.regioncusco.llankasun.server.dtos.MetaDto;
import pe.regioncusco.llankasun.server.dtos.ResidenteResumenDto;
import pe.regioncusco.llankasun.server.dtos.UsuarioDto;
import pe.regioncusco.llankasun.server.dtos.UsuarioResumenDto;
import pe.regioncusco.llankasun.server.enums.Device;
import pe.regioncusco.llankasun.server.exceptions.BadRequestException;
import pe.regioncusco.llankasun.server.models.entities.Residente;
import pe.regioncusco.llankasun.server.models.repositorys.ResidenteRespository;
import pe.regioncusco.llankasun.server.services.ResidenteService;
import pe.regioncusco.llankasun.server.services.UsuarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private static final Logger LOG = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired private AccessTokenImpl accessToken;
    @Autowired private ResidenteService residenteService;
    @Autowired private UtilConfig utilConfig;

    @Autowired
    private ResidenteRespository residenteRespository;

    @Override
    public Tpl verDatosUsuario() {
        if(!verifyAccount()){
            return Utils.tpl(false, "anonymousUser");
        }

        String usuario = accessToken.getUserId();
        Residente residente = residenteRespository.findById(usuario).orElse(null);
        if(residente == null){
            LOG.warn("El usuario con id {} no existe.", usuario);
            return Utils.tpl(false,  String.format("Usuario con ndoc %s, existe. ", usuario));
        }

        UsuarioDto usuarioDto = new UsuarioDto(
                residente.getNumDocumento(),
                residente.getNombres() + " " + residente.getApellidos(),
                residente.getCargo(),
                residente.getPerfil(),
                residente.getEmail(),
                residente.getTelefono(),
                residente.isEnable()
        );
        List<UsuarioDto> listUser = new ArrayList<>();
        listUser.add(usuarioDto);
        LOG.info("Perfil del usuario {}.", usuario);
        return Utils.tpl(true, listUser, listUser.size());
    }

    @Override
    public Tpl listarMetasUsuario() {
        if(!verifyAccount()){
            return Utils.tpl(false, "anonymousUser");
        }
        return getMetasPorResidente(accessToken.getUserId());
    }

    @Override
    public Tpl verUsuario() {
        if(!verifyAccount()){
            return Utils.tpl(false, "anonymousUser");
        }

        List<UsuarioResumenDto> list = new ArrayList<>();
        list.add(new UsuarioResumenDto(accessToken.getUserId(), accessToken.getUserName(), accessToken.getUserRole()));
        return Utils.tpl(true, list, list.size());
    }

    @Override
    public Tpl verResumenUsuario() {
        if(!verifyAccount()){
            return Utils.tpl(false, "anonymousUser");
        }
        Residente residente = residenteRespository.findById(accessToken.getUserId()).orElse(null);
        if(residente == null){
            LOG.warn("Residente con num doc {} no existe", accessToken.getUserId());
            return Utils.tpl(false, "No existe usuario");
        }
        List<ResidenteResumenDto> list = new ArrayList<>();
        list.add(new ResidenteResumenDto(residente.getNombres(), residente.getApellidos(), residente.getCargo().getDescripcion()));
        return Utils.tpl(true, list, list.size());
    }

    @Override
    public Tpl getMetasPorResidente(String documento){
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://tareo.regioncusco.gob.pe/tareos/servicios/listar_metas_habilitadas?dni=" + documento + "&anio=2022";
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        JSONParser parser = new JSONParser();
        List<MetaDto> list = new ArrayList<>();
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(response.getBody());
            List<Map<String, Object>> data = (List<Map<String, Object>>) jsonObj.get("data");
            for(Map<String, Object> object: data){
                Long numero = (Long) object.get("nro_meta");
                String programa = (String) object.get("programa");
                String proyecto = (String) object.get("proyecto");
                String actividad = (String) object.get("actividad");
                String funcion = (String) object.get("funcion");
                String divisionFuncional = (String) object.get("division_funcional");
                String grupoFuncional = (String) object.get("grupo_funcional");
                String nombreMeta = (String) object.get("obra_desc");
                MetaDto meta = new MetaDto(
                        String.format("%04d", numero),
                        programa,
                        proyecto,
                        actividad,
                        funcion,
                        divisionFuncional,
                        grupoFuncional,
                        nombreMeta.length() > 100 ? nombreMeta.substring(0,99) : nombreMeta
                );
                list.add(meta);
            }
        } catch (ParseException e) {
            LOG.warn("Error al realizar la lectura de datos...");
            return Utils.tpl(false, "");
        }
        return Utils.tpl(true, list, list.size());
    }

    @Override
    public Tpl userByOrigen(String plataforma) {
        String usuario = accessToken.getUserId();
        Residente residente = residenteRespository.findById(usuario).orElse(null);
        if(residente == null){
            LOG.warn("El usuario con id {} no existe.", usuario);
            return Utils.tpl(false,  String.format("Usuario con ndoc %s, existe. ", usuario));
        }
        String idPlataforma = Utils.decodeBase64(plataforma);
        if(idPlataforma.equals(Device.IPHONE.key()) && residente.getOrigen() == Device.IPHONE.value()){
            return Utils.tpl(true, "");
        }
        return Utils.tpl(false, "Ud. no tiene acceso al aplicativo.");
    }

    @Override
    public void setEnabledAndPerfil(String username, boolean enabled, String perfil) {
        try {
            UserResource userResource = getUserResource(username);
            List<RoleRepresentation> roles =  userResource.roles().realmLevel().listAll();
            LOG.info("Roles del usuario {}, {}", username, roles.size());

            userResource.roles().realmLevel().add(roles(perfil, getRealmResource()));
            LOG.info("ID {}, enabled {}", userResource.toRepresentation().getId(), enabled);
        } catch (Exception ex){
            LOG.info("Error al crear perfiles para {}, {}", username, ex.getLocalizedMessage());
            throw new BadRequestException("Ocurrio un error al bloquear");
        }
    }

    @Override
    public void changePerfil(String username, String perfil) {
        try {
            UserResource userResource = getUserResource(username);
            List<RoleRepresentation> roles =  userResource.roles().realmLevel().listAll();
            LOG.info("Roles del usuario {}, {}", username, roles.size());

            userResource.roles().realmLevel().remove(roles);
            List<RoleRepresentation> rolesNuevos =  roles(perfil, getRealmResource());

            userResource.roles().realmLevel().add(roles);
            userResource.roles().realmLevel().add(rolesNuevos);
            LOG.info("ID {}, Perfil cambiado {}", userResource.toRepresentation().getId());
        } catch (Exception ex){
            LOG.info("Error al cambiar perfil del {}, {}", username, ex.getLocalizedMessage());
            throw new BadRequestException("Ocurrio un error al bloquear");
        }
    }

    @Override
    public void removePerfil(String username, String perfil) {
        try {
            UserResource userResource = getUserResource(username);
            List<RoleRepresentation> roles =  userResource.roles().realmLevel().listAll();
            LOG.info("Roles del usuario {}, {}", username, roles.size());

            List<RoleRepresentation> rolesNuevos =  roles(perfil, getRealmResource());
            userResource.roles().realmLevel().remove(rolesNuevos);
            LOG.info("Perfil cambiado para {}", userResource.toRepresentation().getId());
        } catch (Exception ex){
            LOG.info("Error al cambiar perfil del {}", ex.getLocalizedMessage());
            throw new BadRequestException("Ocurrio un error al bloquear");
        }
    }

    public List<UserRepresentation> findByUsername(String username) {
        Residente residente = residenteService.find(username);
        Keycloak keycloak = utilConfig.keycloak();
        List<UserRepresentation> users = keycloak.realm(ParamsManager.REALM).users().search(residente.getNumDocumento());
        return users;
    }

    private UserResource getUserResource(String username){
        List<UserRepresentation> users = findByUsername(username);
        String id = users.get(0).getId();
        LOG.info("Usuario ID {}", id);
        Keycloak keycloak = utilConfig.keycloak();
        RealmResource realmResource = keycloak.realm(ParamsManager.REALM);
        UsersResource usersRessource = realmResource.users();

        UserResource userResource = usersRessource.get(id);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userResource.update(userRepresentation);
        return userResource;
    }

    private RealmResource getRealmResource(){
        return utilConfig.keycloak().realm(ParamsManager.REALM);
    }

    private List<RoleRepresentation> roles(String perfil, RealmResource realmResource){
        List<RoleRepresentation> roles = new ArrayList<>();
        if(perfil.equals("ADMINISTRADOR")){
            roles.add(realmResource.roles().get(ParamsManager.REALM_ADMIN).toRepresentation());
            roles.add(realmResource.roles().get(ParamsManager.REALM_USER).toRepresentation());
            return roles;
        }
        roles.add(realmResource.roles().get(ParamsManager.REALM_USER).toRepresentation());
        return roles;
    }

    private boolean verifyAccount(){
        if(accessToken.getPrincipal().equals("anonymousUser")){
            LOG.warn("El usuario esta como anonymousUser ");
            return false;
        }
        return true;
    }

}
