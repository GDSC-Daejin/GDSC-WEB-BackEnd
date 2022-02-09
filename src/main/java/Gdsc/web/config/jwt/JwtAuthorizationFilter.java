package Gdsc.web.config.jwt;

import Gdsc.web.config.auth.PrincipalDetails;
import Gdsc.web.domain.Member;
import Gdsc.web.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 시큐리티가 filter가지고 있는데 그 필터중에 베이직 어썬틱케이션 필터라는것이 있는데
// 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무저건 타게 되어있음
// 만약 권한이나 인증이 필요한 주소가 아니라면 필터를 안탐

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private MemberRepository memberRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
   /*     System.out.println(request.getHeader("host"));
        System.out.println(request.getServletPath() +" 인증이나 권한이 필요한 주소 \n");*/
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        //System.out.println("jwtHeader: " + jwtHeader);

        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
        //System.out.println(username);
        if(username != null){
            Member memberEntity = memberRepository.findByUsername(username);
            PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
            // jwt 토큰 서명을 통해서 서명 정상이면 authentication 객체 저장
            //System.out.println(principalDetails.getAuthorities());
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            //강제로 시큐리티 세션에 접근하여 로긴
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    }
}
