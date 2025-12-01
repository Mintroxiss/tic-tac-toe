package ru.danil.shkuratetskiy.tic_tac_toe.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.AuthService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.SignUpRequest;

import java.util.Base64;
import java.util.UUID;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignUpRequest request) {
        boolean ok = authService.register(request);
        return ok ? ResponseEntity.ok("OK") : ResponseEntity.badRequest().body("Login exists");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String header) {
        String base64 = header.replace("Basic ", "");
        String decoded = new String(Base64.getDecoder().decode(base64));
        String[] parts = decoded.split(":", 2);
        UUID id = authService.login(parts[0], parts[1]);
        return id != null ? ResponseEntity.ok("OK") : ResponseEntity.badRequest().body("Invalid credentials");
    }
}
