package ru.danil.shkuratetskiy.tic_tac_toe.datasource.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {
    public UserEntity() {}

    public UserEntity(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
