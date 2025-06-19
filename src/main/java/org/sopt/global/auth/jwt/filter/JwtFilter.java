package org.sopt.global.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.global.auth.jwt.util.JwtUtil;
import org.sopt.global.auth.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

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
        String username = jwtUtil.getUsername(token);

        CustomUserDetails userDetails = new CustomUserDetails(id, username, null);


        // 나중에 권한 추가되면 권한 추가
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList()));
    }
}
