package Gdsc.web.common.config.security;


import Gdsc.web.common.config.properties.AppProperties;
import Gdsc.web.member.model.RoleType;
import Gdsc.web.oauth.exception.RestAuthenticationEntryPoint;
import Gdsc.web.oauth.filter.TokenAuthenticationFilter;
import Gdsc.web.oauth.handler.OAuth2AuthenticationFailureHandler;
import Gdsc.web.oauth.handler.OAuth2AuthenticationSuccessHandler;
import Gdsc.web.oauth.handler.TokenAccessDeniedHandler;
import Gdsc.web.oauth.handler.TokenValidateInterceptor;
import Gdsc.web.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import Gdsc.web.oauth.service.CustomOAuth2UserService;
import Gdsc.web.oauth.service.CustomUserDetailsService;
import Gdsc.web.oauth.token.AuthTokenProvider;
import Gdsc.web.oauth.repository.UserRefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    private final AppProperties appProperties;

    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    /*
     * UserDetailsService ??????
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

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
                .antMatchers("/api/guest/**").permitAll() // TokenValidateInterceptor ?????? ????????? ???????????? ?????? ??? ???
                /*.antMatchers("/api/member/**").hasAnyAuthority(RoleType.MEMBER.getCode() , RoleType.LEAD.getCode() , RoleType.CORE.getCode())
                .antMatchers("/api/admin/**").hasAnyAuthority(RoleType.LEAD.getCode() , RoleType.CORE.getCode())*/ // admin core api ??? interceptor ??? ?????? ????????? ????????? ??????
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*
     * auth ????????? ??????
     * */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
     * security ?????? ???, ????????? ????????? ??????
     * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * ?????? ?????? ??????
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /*
     * ?????? ?????? ?????? Repository
     * ?????? ????????? ?????? ?????? ????????? ??? ??????.
     * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
     * Oauth ?????? ?????? ?????????
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    /*
     * Oauth ?????? ?????? ?????????
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }


}