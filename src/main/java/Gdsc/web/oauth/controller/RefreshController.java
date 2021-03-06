package Gdsc.web.oauth.controller;

import Gdsc.web.common.config.properties.AppProperties;
import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.oauth.entity.UserRefreshToken;
import Gdsc.web.member.model.RoleType;
import Gdsc.web.oauth.token.AuthToken;
import Gdsc.web.oauth.token.AuthTokenProvider;
import Gdsc.web.oauth.repository.UserRefreshTokenRepository;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.oauth.utils.HeaderUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class RefreshController {
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";
    @GetMapping("/refresh")
    @ApiOperation(value = "refresh 토큰을 이용하여 JWT 토큰 재발급", notes = "토큰이 expired 되어야 작동함")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT 토큰 Bearer 값 필수 ", required = true, paramType = "header", dataType = "string", defaultValue = "Bearer "),
            @ApiImplicitParam(name = "RefreshToken", value = "refresh 토큰 Bearer 값 필수", required = true, paramType = "header", dataType = "string" ,defaultValue = "Bearer ")
    })
    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validateWithOutExpired()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token 인지 확인
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // refresh token
        String refreshToken = HeaderUtil.getHeaderRefreshToken(request);
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);
        log.info("refreshToken: {}", refreshToken);

        if (!authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());
            userRefreshTokenRepository.save(userRefreshToken);
        }
        Authentication authentication = tokenProvider.getAuthentication(newAccessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String,String>  tokenMap = new HashMap<>();
        tokenMap.put("token", newAccessToken.getToken());
        return ApiResponse.success("data", tokenMap );
    }
}
