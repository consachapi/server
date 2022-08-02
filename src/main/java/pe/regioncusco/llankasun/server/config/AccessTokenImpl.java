package pe.regioncusco.llankasun.server.config;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AccessTokenImpl {
    private AccessToken accessToken;

    public String getUserId(){
        accessToken();
        if(accessToken == null){
            return "";
        }
        return accessToken.getPreferredUsername();
    }

    public String getUserName(){
        if(accessToken == null){
            return "";
        }
        return accessToken.getName();
    }

    public Set<String> getUserRole(){
        accessToken();
        if(accessToken == null){
            return null;
        }

        if(accessToken.getRealmAccess() == null){
            return null;
        }
        return accessToken.getRealmAccess().getRoles();
    }

    public String getPrincipal(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    private void accessToken(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            accessToken = null;
        } else {
            KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            accessToken = authentication.getAccount().getKeycloakSecurityContext().getToken();
        }
    }
}
