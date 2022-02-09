package Gdsc.web.config;


import Gdsc.web.config.auth.PrincipalDetailService;
import Gdsc.web.config.jwt.JwtAuthenticationEntryPoint;
import Gdsc.web.config.jwt.JwtRequestFilter;
import Gdsc.web.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import Gdsc.web.config.oauth.OAuth2AuthenticationFailureHandler;
import Gdsc.web.config.oauth.OAuth2AuthenticationSuccessHandler;
import Gdsc.web.config.oauth.auth.service.CustomOauth2UserService;
import Gdsc.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;



@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CorsFilter corsFilter;
    private final MemberRepository memberRepository;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private PrincipalDetailService principalDetailService;
    @Autowired
    private CustomOauth2UserService customOauth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(principalDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //System.out.println(ipAddress);
        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .formLogin()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/member")
                .access("hasRole('ROLE_MEMBER') or hasRole('ROLE_CORE') or hasRole('ROLE_LEAD')")
                .antMatchers("/api/core/**")
                .access("hasRole('ROLE_CORE') or hasRole('ROLE_LEAD')")
                .antMatchers("/api/lead/**")
                .access("hasRole('ROLE_LEAD')")
                .antMatchers("/oauth")
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                .baseUri("/oauth2/authorization")
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(customOauth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler((AuthenticationFailureHandler) oAuth2AuthenticationFailureHandler);
                //.antMatchers("/swagger-ui.html/**")
                //.access("hasIpAddress('0:0:0:0:0:FFFF:3A4E:7728') or hasIpAddress('fe80::9968:13fc:755a:4016/24')" )
               /* .antMatchers("/api/member/**")
                .access("hasRole('ROLE_MEMBER') or hasRole('ROLE_CORE') or hasRole('ROLE_LEAD')")
                .antMatchers("/api/core/**")
                .access("hasRole('ROLE_CORE') or hasRole('ROLE_LEAD')")
                .antMatchers("/api/lead/**")
                .access("hasRole('ROLE_LEAD')")*/

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
