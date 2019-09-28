package cerulean.hw1.Controllers;

import cerulean.hw1.Models.Account;
import cerulean.hw1.Database.GameRepository;
import cerulean.hw1.Models.Game;
import cerulean.hw1.Models.GameComponents.Move;
import cerulean.hw1.Services.GameService;
import cerulean.hw1.Services.MongoDBUserDetailsManager;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private MongoDBUserDetailsManager mongoDBUserDetailsManager;
    @Autowired
    private GameService gameService;


    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String getGames() {
        UserDetails principalUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principalUser.getUsername();
        Account account = mongoDBUserDetailsManager.loadAccountByUsername(username);
        Gson gson = new Gson();
        return gson.toJson(account.getGames());
    }
    @RequestMapping(value ="/get/{gameId}", method = RequestMethod.GET)
    public String getGame(@PathVariable String gameId) {
        return gameService.getGame(gameId);
    }

    @RequestMapping(value ="/new", method = RequestMethod.GET)
    public String newGame() {
        UserDetails principalUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principalUser.getUsername();
        Account account = mongoDBUserDetailsManager.loadAccountByUsername(username);
        Game game = new Game(username);
        gameService.setGame(new Game(username));
        account.getGames().add(game.getGameId());
        mongoDBUserDetailsManager.persistAccount(account);

        return game.getGameId();
    }
    @RequestMapping(value ="/move", method = RequestMethod.POST)
    public void move(@RequestBody String gameId, int[] to, int[] from) throws Exception {
        Game game = new Gson().fromJson(gameService.getGame(gameId), Game.class);


        Move playeMove = game.move(to, from,true);
        int[] ai_coords = game.runAI();
        Move aiMove = game.move(new int[]{ai_coords[0], ai_coords[1]}, new int[]{ai_coords[2], ai_coords[3]},false);

    }

}
