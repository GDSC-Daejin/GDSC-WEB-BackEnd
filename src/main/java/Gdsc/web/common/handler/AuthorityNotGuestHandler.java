package Gdsc.web.common.handler;

import Gdsc.web.member.model.RoleType;
import Gdsc.web.oauth.token.AuthToken;
import Gdsc.web.oauth.token.AuthTokenProvider;
import Gdsc.web.oauth.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletResponse;
@Component
@RequiredArgsConstructor
@Slf4j
public
class AuthorityNotGuestHandler implements HandlerInterceptor {
    private final AuthTokenProvider tokenProvider;


    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if(!authToken.getTokenClaims().get("role").equals(RoleType.GUEST.getCode())){
            return true;
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        return false;
    }
}
