package ru.danil.shkuratetskiy.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.danil.shkuratetskiy.ui.model.GameDto;
import ru.danil.shkuratetskiy.ui.model.LoginResult;

import java.util.*;

@Service
public class ApiClient {
    private final RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String baseUrl;

    @Autowired
    public ApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean signup(String login, String password) {
        try {
            restTemplate.exchange(
                    baseUrl + "/api/auth/signup",
                    HttpMethod.POST,
                    new HttpEntity<>(Map.of("login", login, "password", password), jsonHeaders()),
                    String.class
            );
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    public LoginResult login(String login, String password) {
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/api/auth/login",
                    HttpMethod.POST,
                    new HttpEntity<>(Map.of("login", login, "password", password), jsonHeaders()),
                    Map.class
            );
            String userId = (String) response.getBody().get("userId");
            String encoded = Base64.getEncoder()
                    .encodeToString((login + ":" + password).getBytes());
            return new LoginResult(userId, "Basic " + encoded);
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public String createGame(boolean vsComputer, String authHeader) {
        try {
            HttpHeaders headers = jsonHeaders();
            if (authHeader != null) headers.set("Authorization", authHeader);
            ResponseEntity<String> response = restTemplate.exchange(
                    baseUrl + "/api/game/new",
                    HttpMethod.POST,
                    new HttpEntity<>(Map.of("vsComputer", vsComputer), headers),
                    String.class
            );
            return response.getBody().replace("\"", "");
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public GameDto getGame(String gameId) {
        try {
            return restTemplate.getForObject(baseUrl + "/api/game/" + gameId, GameDto.class);
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public GameDto makeMove(String gameId, int row, int col, String authHeader) {
        try {
            HttpHeaders headers = jsonHeaders();
            if (authHeader != null) headers.set("Authorization", authHeader);
            return restTemplate.exchange(
                    baseUrl + "/api/game/" + gameId + "/move",
                    HttpMethod.POST,
                    new HttpEntity<>(Map.of("row", row, "col", col), headers),
                    GameDto.class
            ).getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public List<GameDto> getAvailableGames() {
        try {
            GameDto[] games = restTemplate.getForObject(
                    baseUrl + "/api/game/available", GameDto[].class);
            return games != null ? Arrays.asList(games) : List.of();
        } catch (HttpClientErrorException e) {
            return List.of();
        }
    }

    public GameDto joinGame(String gameId, String authHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (authHeader != null) headers.set("Authorization", authHeader);
            return restTemplate.exchange(
                    baseUrl + "/api/game/" + gameId + "/join",
                    HttpMethod.POST,
                    new HttpEntity<>(null, headers),
                    GameDto.class
            ).getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public void cancelWaitingGames(String authHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (authHeader != null) headers.set("Authorization", authHeader);
            restTemplate.exchange(
                    baseUrl + "/api/game/waiting",
                    HttpMethod.DELETE,
                    new HttpEntity<>(null, headers),
                    Void.class
            );
        } catch (HttpClientErrorException ignored) {}
    }

    private HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
