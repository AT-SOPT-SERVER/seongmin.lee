package org.sopt.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.auth.jwt.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        Optional<String> tokenOpt = jwtUtil.extractToken(request);
        if(tokenOpt.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenOpt.get();

        if(!jwtUtil.validateToken(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if(jwtUtil.isExpired(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        saveAuthenticationAtSecurityContextHolder(token);

        filterChain.doFilter(request, response);

    }

    private void saveAuthenticationAtSecurityContextHolder(String token) {
        Long id = jwtUtil.getId(token);

        // 나중에 권한 추가되면 권한 추가
        String username = jwtUtil.getUsername(token);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(id, username, Collections.emptyList()));
    }
}
