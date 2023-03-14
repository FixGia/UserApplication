package fr.bewee.userapplication.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig {

    @Lazy
    private final LoginFilter loginFilter;
    private final JwtFilter jwtFilter;

    public WebSecurityConfig(LoginFilter loginFilter, JwtFilter jwtFilter) {
        this.loginFilter = loginFilter;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
        uds.createUser(User.builder().username("user").password("userPassword").roles("USER").build());
        uds.createUser(User.builder().username("admin").password("adminPassword").roles("ADMIN","USER").build());

        return uds;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {

        var dao = new DaoAuthenticationProvider();

        dao.setUserDetailsService(userDetailsService);

        return new ProviderManager();
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterAt(loginFilter, BasicAuthenticationFilter.class);
       http.addFilterAt(jwtFilter, BasicAuthenticationFilter.class);
        http.exceptionHandling()
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                   response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().println(accessDeniedException.getMessage());
               })).authenticationEntryPoint(((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                   response.getWriter().println(authException.getMessage());
                }));
        return http.build();
    }
}
