package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.UserEntity;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.SignUpRequest;

import java.util.UUID;

@Service
public class AuthService {
    private final UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public boolean register(SignUpRequest request) {
        return userService.register(request.getLogin(), request.getPassword());
    }

    public UUID login(String login, String password) {
        return userService.findByLogin(login)
                .filter(u -> u.getPassword().equals(password))
                .map(UserEntity::getId)
                .orElse(null);
    }
}
