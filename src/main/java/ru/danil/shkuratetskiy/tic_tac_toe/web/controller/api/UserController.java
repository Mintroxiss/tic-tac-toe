package ru.danil.shkuratetskiy.tic_tac_toe.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.UserService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.UserDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID userId) {
        return userService.findById(userId)
                .map(u -> ResponseEntity.ok(new UserDto(u.getId(), u.getLogin())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
