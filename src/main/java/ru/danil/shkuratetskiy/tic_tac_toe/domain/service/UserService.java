package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.UserEntity;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean register(String login, String password) {
        if (repository.findByLogin(login).isPresent()) {
            return false;
        }
        repository.save(new UserEntity(login, password));
        return true;
    }

    public Optional<UserEntity> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Optional<UserEntity> findById(UUID id) {
        return repository.findById(id);
    }
}
