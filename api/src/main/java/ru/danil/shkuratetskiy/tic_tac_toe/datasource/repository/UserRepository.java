package ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository;

import org.springframework.data.repository.CrudRepository;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLogin(String login);
}
