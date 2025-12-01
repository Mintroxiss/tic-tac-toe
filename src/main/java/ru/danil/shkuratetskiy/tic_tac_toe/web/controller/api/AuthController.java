package ru.danil.shkuratetskiy.tic_tac_toe.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.AuthService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.LoginResponse;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.SignUpRequest;

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
    public ResponseEntity<?> login(@RequestBody SignUpRequest request) {
        UUID id = authService.login(request.getLogin(), request.getPassword());
        if (id != null) {
            return ResponseEntity.ok(new LoginResponse(id));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

}
