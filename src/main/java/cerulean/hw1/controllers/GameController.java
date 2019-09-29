package cerulean.hw1.controllers;

import cerulean.hw1.models.Account;
import cerulean.hw1.models.Game;
import cerulean.hw1.models.gameComponents.Move;
import cerulean.hw1.services.GameService;
import cerulean.hw1.services.MongoDBUserDetailsManager;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @RequestMapping(value ="/startGame", method = RequestMethod.POST)
    public String newGame(@RequestBody String board) {
        UserDetails principalUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principalUser.getUsername();
        Account account = mongoDBUserDetailsManager.loadAccountByUsername(username);
        Game game = new Game(username);
        ArrayList<Integer> b = new Gson().fromJson(board, ArrayList.class);
        System.out.print(board);

//        game.getBoard().postBoard(b);
//        gameService.save(new Game(username));
//        account.getGames().add(game.getGameId());
//        mongoDBUserDetailsManager.persistAccount(account);

        return game.getGameId();
    }
    @RequestMapping(value ="/move", method = RequestMethod.POST)
    public void move(@RequestBody String gameId, int[] to, int[] from) throws Exception {
        Game game = new Gson().fromJson(gameService.getGame(gameId), Game.class);


        int[] ai_coords = game.runAI();

        Move playeMove = game.move(to, from, true);
        Move aiMove = game.move(new int[]{ai_coords[0], ai_coords[1]}, new int[]{ai_coords[2], ai_coords[3]},false);
        gameService.save(game);

    }

}
