package ru.danil.shkuratetskiy.tic_tac_toe.web.controller.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Game;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.Winner;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.GameService;
import ru.danil.shkuratetskiy.tic_tac_toe.web.mapper.GameDtoMapper;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameDto;
import ru.danil.shkuratetskiy.tic_tac_toe.web.model.GameStatus;

import java.util.UUID;

@Controller
@RequestMapping("/game")
public class GamePageController {
    private final GameService gameService;

    @Autowired
    public GamePageController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/new")
    public RedirectView createNewGame() {
        UUID newGameId = gameService.createNewGame();
        return new RedirectView("/game/" + newGameId + "/view");
    }

    @GetMapping("/{userId}/view")
    public String viewGame(@PathVariable UUID id, Model model) throws JsonProcessingException {
        Game game = gameService.getGameById(id);
        if (game == null) return "error";

        GameDto gameDto = GameDtoMapper.toGameDto(game);

        ObjectMapper mapper = new ObjectMapper();
        String fieldJson = mapper.writeValueAsString(gameDto.getField());

        model.addAttribute("gameId", id);
        model.addAttribute("field", gameDto.getField());
        model.addAttribute("fieldJson", fieldJson);
        model.addAttribute("status", gameDto.getStatus());

        return "game";
    }

    @GetMapping("/{userId}/result")
    public String gameResult(@PathVariable UUID id, Model model) {
        Game game = gameService.getGameById(id);
        if (game == null) return "error";

        GameDto gameDto = GameDtoMapper.toGameDto(game);

        model.addAttribute("field", gameDto.getField());
        String message = switch (gameService.isGameOver(game)) {
            case Winner.PLAYER1 -> GameStatus.WIN.getMessage();
            case Winner.PLAYER2 -> GameStatus.LOSS.getMessage();
            case DRAW -> GameStatus.DRAW.getMessage();
        } + "!";
        model.addAttribute("resultText", message);

        return "game-result";
    }
}
