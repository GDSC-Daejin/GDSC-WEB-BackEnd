package Gdsc.web.config.jwt;

import Gdsc.web.config.auth.PrincipalDetails;
import Gdsc.web.controller.dto.JwtDto;
import Gdsc.web.domain.Member;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  {
        System.out.println("JwtAuthenticationFilter : 진입");

        try {
            //ObjectMapper om = new ObjectMapper();
            Member member = objectMapper.readValue(request.getInputStream(), Member.class);

            System.out.println(member);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            System.out.println("로그인 완료됨 : " + principalDetails.getMember().getUsername());
            return authentication;
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("로그인실패");

        return null;
    }

    //위 함수 실행후 인증 잘되면 실행
    // 여기서 jwt 토큰 만들어서 요청한 사용자에게 토큰 발급
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //System.out.println("successfulAuthentication 실행 : 인증 완료 ");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String jwtToken = JWT.create()
                .withSubject(JwtProperties.SECRET) // 토큰 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getMember().getId())
                .withClaim("username", principalDetails.getMember().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        response.addHeader(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX+jwtToken);

        //body에 authentication
        JwtDto jwtDto = new JwtDto();
        jwtDto.setAuthorization(JwtProperties.TOKEN_PREFIX+jwtToken);
        jwtDto.setStatus(HttpStatus.OK);
        response.getWriter().write(objectMapper.writeValueAsString(jwtDto));


    }
}
