package Gdsc.web.oauth.handler;

import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.oauth.token.AuthToken;
import Gdsc.web.oauth.token.AuthTokenProvider;
import Gdsc.web.oauth.utils.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidateInterceptor implements HandlerInterceptor {
    private final AuthTokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            String accessToken = HeaderUtil.getAccessToken(request);
            AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
            if(!authToken.validate()){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return false;
            }

            return true;

    }
}
