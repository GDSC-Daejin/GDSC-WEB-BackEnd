package Gdsc.web.common.config.security;


import Gdsc.web.common.config.properties.AppProperties;

import Gdsc.web.oauth.exception.RestAuthenticationEntryPoint;
import Gdsc.web.oauth.filter.TokenAuthenticationFilter;
import Gdsc.web.oauth.handler.TokenAccessDeniedHandler;
import Gdsc.web.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    private final AppProperties appProperties;

    private final AuthTokenProvider tokenProvider;

    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    /*
     * UserDetailsService 설정
     * */


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .and()
                .addFilter(corsFilter)
                .authorizeRequests()
                .antMatchers("/api/support/limit").permitAll()
                .antMatchers("/api/guest/**").permitAll() // TokenValidateInterceptor 에서 어차피 걸러져서 권한 다 줌
                /*.antMatchers("/api/member/**").hasAnyAuthority(RoleType.MEMBER.getCode() , RoleType.LEAD.getCode() , RoleType.CORE.getCode())
                .antMatchers("/api/admin/**").hasAnyAuthority(RoleType.LEAD.getCode() , RoleType.CORE.getCode())*/ // admin core api 는 interceptor 로 잡기 때문에 이렇게 안함
                .anyRequest().permitAll();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*
     * auth 매니저 설정
     * */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
     * security 설정 시, 사용할 인코더 설정
     * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }


}