package Gdsc.web.oauth.controller;

import Gdsc.web.common.config.properties.AppProperties;
import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.common.dto.ResponseDto;
import Gdsc.web.oauth.dto.AuthReqModel;
import Gdsc.web.oauth.entity.UserRefreshToken;
import Gdsc.web.member.entity.Member;
import Gdsc.web.oauth.entity.UserPrincipal;
import Gdsc.web.oauth.token.AuthToken;
import Gdsc.web.oauth.token.AuthTokenProvider;
import Gdsc.web.oauth.repository.UserRefreshTokenRepository;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.oauth.utils.CookieUtil;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private  final MemberService memberService;
    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";
    @Profile("!real")
    @ApiOperation(value = "회원가입 테스트용", notes = "회원가입 할때 쓰는 놈 Api 테스트 용으로 삭제 예정")
    @PostMapping("/test/auth/join")
    public ResponseDto<Integer> join(@RequestBody Member member) {

        memberService.회원가입(member);
        // 수정필요
        return new ResponseDto<Integer>(HttpStatus.OK, 1, "성공");
    }

    @ApiOperation(value = "로그인 테스트", notes = "로그인 할때 쓰는 놈 Api 테스트 용으로 삭제 예정")
    @PostMapping("/test/auth/login")
    @Profile("!real")
    public ApiResponse login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthReqModel authReqModel
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReqModel.getId(),
                        authReqModel.getPassword()
                )
        );
        //System.out.println(authReqModel.getPassword());
        String userId = authReqModel.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // 여기서 업데이트 쿼리가 안나옴
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }


}