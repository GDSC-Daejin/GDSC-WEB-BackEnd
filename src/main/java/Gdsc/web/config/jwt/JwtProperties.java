package Gdsc.web.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public interface JwtProperties {

    String SECRET = "POWER_DEVELOPER";
    int EXPIRATION_TIME = 60000*20; // 20분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}
