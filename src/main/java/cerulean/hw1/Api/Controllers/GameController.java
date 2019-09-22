package cerulean.hw1.Api.Controllers;

import cerulean.hw1.Api.Models.Account.Account;
import cerulean.hw1.Database.AccountRepository;
import cerulean.hw1.Database.GameSessionRepository;
import cerulean.hw1.Database.LoginSessionRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private LoginSessionRepository loginSessionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private GameSessionRepository gameSessionRepository;

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String getGames(@RequestBody String username, String password) {
        Gson gson = new Gson();
        Account account = gson.fromJson(accountRepository.get(username, password), Account.class);
        return new Gson().toJson(account.getGameSessions());
    }
    @RequestMapping(value ="/{gameSessionId}", method = RequestMethod.GET)
    public String getGame(@RequestBody String gameSessionId) {
        return gameSessionRepository.get(gameSessionId);
    }
    @RequestMapping(value ="/game", method = RequestMethod.POST)
    public String newGame(@RequestBody String username, String loginSession) {
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
