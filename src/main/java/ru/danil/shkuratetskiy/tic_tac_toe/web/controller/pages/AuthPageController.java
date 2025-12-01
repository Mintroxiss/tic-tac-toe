package ru.danil.shkuratetskiy.tic_tac_toe.web.controller.pages;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.AuthService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.SignUpRequest;

import java.util.UUID;

@Controller
@RequestMapping("/auth")
public class AuthPageController {

    private final AuthService authService;

    @Autowired
    public AuthPageController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/signup")
    public String register() {
        return "auth-signup";
    }

    @PostMapping("/signup")
    public String doRegister(@RequestParam String login,
                             @RequestParam String password,
                             Model model) {
        boolean ok = authService.register(new SignUpRequest(login, password));
        if (!ok) {
            model.addAttribute("error", "Логин уже занят");
            return "auth-signup";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login() {
        return "auth-login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String login,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        UUID userId = authService.login(login, password);
        if (userId == null) {
            model.addAttribute("error", "Неверный логин или пароль");
            return "auth-login";
        }

        session.setAttribute("userId", userId);
        return "redirect:/";
    }
}

