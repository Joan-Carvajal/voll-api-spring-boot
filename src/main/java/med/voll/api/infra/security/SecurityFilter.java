package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        var authHearder = request.getHeader(HttpHeaders.AUTHORIZATION);
       if (authHearder!=null ){
        var token =authHearder.substring(7);
           var subject= tokenService.getSubject(token);
           if (subject !=null){
               //token valido
               var usuario = usuarioRepository.findByLogin(subject);
               var authenticacion = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(authenticacion);
           }
        }
           filterChain.doFilter(request,response);

    }
}
