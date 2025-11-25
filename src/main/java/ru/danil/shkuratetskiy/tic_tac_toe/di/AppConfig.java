package ru.danil.shkuratetskiy.tic_tac_toe.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository.GameRepository;
import ru.danil.shkuratetskiy.tic_tac_toe.datasource.repository.InternalGameRepository;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.GameService;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.MinimaxGameService;

@Configuration
public class AppConfig {
    @Bean
    public GameRepository gameRepository() {
        return new InternalGameRepository();
    }

    @Bean
    public GameService gameService(GameRepository gameRepository) {
        return new MinimaxGameService(gameRepository);
    }
}
