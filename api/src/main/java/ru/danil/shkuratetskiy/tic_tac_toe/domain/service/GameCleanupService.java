package ru.danil.shkuratetskiy.tic_tac_toe.domain.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository.GameRepository;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.GameState;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class GameCleanupService {
    private final GameRepository repository;

    @Value("${game.waiting.ttl-minutes:30}")
    private int ttlMinutes;

    @Autowired
    public GameCleanupService(GameRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRateString = "${game.waiting.cleanup-interval-ms:60000}")
    @Transactional
    public void deleteExpiredWaitingGames() {
        Instant cutoff = Instant.now().minus(ttlMinutes, ChronoUnit.MINUTES);
        repository.deleteByStateAndCreatedAtBefore(GameState.WAITING.name(), cutoff);
    }
}
