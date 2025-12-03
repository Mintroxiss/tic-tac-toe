package ru.danil.shkuratetskiy.ui.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.danil.shkuratetskiy.ui.model.LoginResult;
import ru.danil.shkuratetskiy.ui.service.ApiClient;

@Controller
@RequestMapping("/auth")
public class AuthPageController {
    private final ApiClient apiClient;

    @Autowired
    public AuthPageController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "auth-signup";
    }

    @PostMapping("/signup")
    public String doSignup(@RequestParam String login, @RequestParam String password, Model model) {
        boolean ok = apiClient.signup(login, password);
        if (!ok) {
            model.addAttribute("error", "Логин уже занят");
            return "auth-signup";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth-login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String login, @RequestParam String password,
                          HttpSession session, Model model) {
        LoginResult result = apiClient.login(login, password);
        if (result == null) {
            model.addAttribute("error", "Неверный логин или пароль");
            return "auth-login";
        }
        session.setAttribute("userId", result.userId());
        session.setAttribute("authHeader", result.authHeader());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
