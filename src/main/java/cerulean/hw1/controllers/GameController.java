package cerulean.hw1.controllers;

import cerulean.hw1.models.Account;
import cerulean.hw1.models.Game;
import cerulean.hw1.models.gameComponents.Move;
import cerulean.hw1.services.GameService;
import cerulean.hw1.services.MongoDBUserDetailsManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
        //return new Gson().toJson(s);
        System.out.println(board);
//        board = board.split(":")[2].replace("}", " ");
//        board = board.replaceAll("null", "-1");

        ArrayList<ArrayList<String>> b = new Gson().fromJson(board, new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        game.getBoard().postBoard(b);
        gameService.save(new Game(username));
        account.getGames().add(game.getGameId());
        mongoDBUserDetailsManager.persistAccount(account);
        return new Gson().toJson(game.getBoard());

    }
    @RequestMapping(value ="/move", method = RequestMethod.POST)
    public void move(@RequestBody String gameId, int[] to, int[] from) throws Exception {
            System.out.print(gameId);
            Game game = new Gson().fromJson(gameService.getGame(gameId), Game.class);


//            to = new int[2];
//            from = new int[2];
//            if(game ==null){
//                return;
//            }
            Move playeMove = game.move(new int[]{2,3}, new int[]{4,3}, true);

            int[] ai_coords = game.runAI();
            Move aiMove = game.move(new int[]{ai_coords[0], ai_coords[1]}, new int[]{ai_coords[2], ai_coords[3]},false);
            gameService.save(game);

    }

}
