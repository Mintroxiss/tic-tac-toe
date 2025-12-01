package ru.danil.shkuratetskiy.tic_tac_toe.domain.model;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.AuthService;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class AuthFilter extends GenericFilterBean {
    private final AuthService authService;

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        if (path.startsWith("/auth") || path.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }
        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            unauthorized(response);
            return;
        }
        String base64 = header.substring(6);
        String decoded = new String(Base64.getDecoder().decode(base64));
        String[] creds = decoded.split(":", 2);

        UUID id = authService.login(creds[0], creds[1]);
        if (id == null) {
            unauthorized(response);
            return;
        }

        chain.doFilter(request, response);
    }

    private void unauthorized(ServletResponse response) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(401);
        resp.setContentType("application/json");
        resp.getWriter().write("Unauthorized");
    }
}
