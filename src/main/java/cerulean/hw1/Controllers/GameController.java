package cerulean.hw1.Controllers;

import cerulean.hw1.Models.Account;
import cerulean.hw1.Database.AccountRepository;
import cerulean.hw1.Database.GameRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private GameRepository gameSessionRepository;

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String getGames(@RequestBody String username, String password) {
        Gson gson = new Gson();
        Account account = gson.fromJson(accountRepository.findByUsername(username).toJson(), Account.class);
        return new Gson().toJson(account.getGameSessions());
    }
    @RequestMapping(value ="/{gameSessionId}", method = RequestMethod.GET)
    public String getGame(@RequestBody String gameId) {
        return gameSessionRepository.findByGameId(gameId).toJson();
    }
    @RequestMapping(value ="/game", method = RequestMethod.POST)
    public String newGame(@RequestBody String username) {
        //TODO: verify logged in session

        //create game session id
        UUID gameSession = UUID.randomUUID();
        //TODO: post account.games.gameSession

        return gameSession.toString();
    }
    @RequestMapping(value ="/move", method = RequestMethod.POST)
    public void move(@RequestBody String gameSession) {
        //TODO: get game by session and deserialize
        //TODO: return ok or error based on move method on game board

    }

}
