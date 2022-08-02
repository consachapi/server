package pe.regioncusco.llankasun.server.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.regioncusco.llankasun.server.commons.ParamsManager;

@Component
public class UtilConfig {
    @Autowired private AccessTokenImpl accessToken;

    public String getUser(){
        if (accessToken.getPrincipal().equals("anonymousUser")){
            return "";
        }
        return accessToken.getUserId();
    }

    public Keycloak keycloak(){
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(ParamsManager.SERVER_URL)
                .realm(ParamsManager.REALM_MASTER)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(ParamsManager.CLIENT_ID)
                .username(ParamsManager.USERNAME)
                .password(ParamsManager.PASSWORD)
                .build();
        return keycloak;
    }
}
