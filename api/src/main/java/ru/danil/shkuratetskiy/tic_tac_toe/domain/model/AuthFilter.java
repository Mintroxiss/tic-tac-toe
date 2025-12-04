package ru.danil.shkuratetskiy.tic_tac_toe.domain.model;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.AuthService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

public class AuthFilter extends GenericFilterBean {

    private final AuthService authService;

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI();

        if (!path.startsWith("/api/") || path.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        boolean gamePathOptionalAuth = path.startsWith("/api/game");

        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            if (gamePathOptionalAuth) {
                chain.doFilter(request, response);
            } else {
                unauthorized(resp);
            }
            return;
        }

        String base64 = header.substring("Basic ".length());
        String decoded;
        try {
            decoded = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            if (gamePathOptionalAuth) {
                chain.doFilter(request, response);
            } else {
                unauthorized(resp);
            }
            return;
        }

        String[] creds = decoded.split(":", 2);
        if (creds.length != 2) {
            if (gamePathOptionalAuth) {
                chain.doFilter(request, response);
            } else {
                unauthorized(resp);
            }
            return;
        }

        UUID userId = authService.login(creds[0], creds[1]);
        if (userId == null) {
            if (gamePathOptionalAuth) {
                chain.doFilter(request, response);
            } else {
                unauthorized(resp);
            }
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private void unauthorized(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType("application/json");
        resp.getWriter().write("{\"error\": \"Unauthorized\"}");
    }
}
