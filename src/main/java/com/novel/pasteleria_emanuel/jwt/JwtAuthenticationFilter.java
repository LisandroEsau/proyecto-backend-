package com.novel.pasteleria_emanuel.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //creamos objetos a nivel de clases paraa validar el token
    private final JwtService jwtService;
    private  final UserDetailsService userDetailsService;
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromRequest(request);
        final String username;
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //obtenemos el username del token
        username = jwtService.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //lo buscamos en la base de datos
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            logger.info("UserDetails: " + userDetails);
            //verificamos si el token es valido, con el metodo isTokenValid
            if(jwtService.isTokenValid(token, userDetails)) {
                //actualizamos el contexto SecurityContextHolder
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                logger.info("authorities: " + userDetails.getAuthorities());
                //seteamo el details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //obtenemos el contexto y seteamos la autenticacion
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")){
            return  authHeader.substring(7);
        }
        return null;
    }

}

