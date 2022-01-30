package Gdsc.web.config;


import Gdsc.web.config.jwt.JwtAuthenticationFilter;
import Gdsc.web.config.jwt.JwtAuthorizationFilter;
import Gdsc.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;



@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CorsFilter corsFilter;
    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //System.out.println(ipAddress);
        http.csrf().disable();
        //세션을 사용하지 않겠다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter) // @Crossorgin은 인증 필요없을떼 사용가능 필터에 등록해야함
                .formLogin().disable() //폼로그인 안씀
                .httpBasic().disable()// 기본적인 http 방식 안씀
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),memberRepository) )
                .authorizeRequests()
                //.antMatchers("/swagger-ui.html/**")
                //.access("hasIpAddress('0:0:0:0:0:FFFF:3A4E:7728') or hasIpAddress('fe80::9968:13fc:755a:4016/24')" )
               /* .antMatchers("/api/member/**")
                .access("hasRole('ROLE_MEMBER') or hasRole('ROLE_CORE') or hasRole('ROLE_LEAD')")
                .antMatchers("/api/core/**")
                .access("hasRole('ROLE_CORE') or hasRole('ROLE_LEAD')")
                .antMatchers("/api/lead/**")
                .access("hasRole('ROLE_LEAD')")*/
                .anyRequest().permitAll();
    }
}
