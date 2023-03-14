package fr.bewee.userapplication.security;

import fr.bewee.userapplication.entity.UserEntity;
import fr.bewee.userapplication.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Map;

@Component
public class LoginFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    public LoginFilter(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;

    }

    private String createJwtToken(Authentication authenticated) {
        UserEntity userEntity = (UserEntity) authenticated.getPrincipal();
        return jwtTokenUtil.generateToken((UserDetails) userEntity);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        Authentication authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        response.setHeader(HttpHeaders.AUTHORIZATION, createJwtToken(authenticated));
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String method= request.getMethod();
        String uri = request.getRequestURI();

        boolean isLogin = HttpMethod.POST.matches(method) && uri.startsWith("/login");

        return !isLogin;
    }
}
