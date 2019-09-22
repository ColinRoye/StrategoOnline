package cerulean.hw1.Api.Controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@RequestMapping("/api/games")
public class GameController {
    @RequestMapping(value ="/", method = RequestMethod.GET)
    public void getGames(@RequestBody String username) {
        //TODO:
    }
    @RequestMapping(value ="/{gameId}", method = RequestMethod.GET)
    public void getGame(@RequestBody String username) {
        //TODO:
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
