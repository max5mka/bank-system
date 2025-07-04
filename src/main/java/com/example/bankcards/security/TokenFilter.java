package com.example.bankcards.security;

import com.example.bankcards.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        String jwt = null;
        String username;
        UserDetails userDetails;
        UsernamePasswordAuthenticationToken auth;
        try {
            String headerAuth = request.getHeader("Authorization");
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                jwt = headerAuth.substring(7);
            }
            if (jwt != null) {
                username = tokenService.getNameFromJwt(jwt);
                if (username != null) {
                    userDetails = authService.loadUserByUsername(username);
                    auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException | ExpiredJwtException e) {
            ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
