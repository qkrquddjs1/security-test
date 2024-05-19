package com.ohgiraffers.securitytest.filter;

import com.ohgiraffers.securitytest.service.AuthService;
import com.ohgiraffers.securitytest.util.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /* 알맞은 내용 작성 */
        String accessToken = TokenUtils.getToken(request.getHeader("Authorization"));
        if(accessToken != null && TokenUtils.isValidToken(accessToken)) {
            String username = TokenUtils.getUsername(accessToken);
            authService.saveAuthentication(username);
        }

        filterChain.doFilter(request, response);

    }
}
