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

    @RequestMapping(value ="/startGame", method = RequestMethod.POST)
    public String newGame(@RequestBody String board) {
        UserDetails principalUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principalUser.getUsername();
        Account account = mongoDBUserDetailsManager.loadAccountByUsername(username);
        String id = UUID.randomUUID().toString();
        Game game = new Game(id);
        //return new Gson().toJson(s);
        System.out.println(board);
//        board = board.split(":")[2].replace("}", " ");
//        board = board.replaceAll("null", "-1");

        ArrayList<ArrayList<String>> b = new Gson().fromJson(board, new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        game.getBoard().postBoard(b);
        gameService.save(game);
        account.getGames().add(game.getGameId());
        mongoDBUserDetailsManager.persistAccount(account);
        return game.getGameId();


    }
    @RequestMapping(value ="/move", method = RequestMethod.POST)
    public String move(@RequestBody String req) throws Exception {
            System.out.println("test" +req + "test");

            String[] s = req.split(":");
            String gameId = s[1].substring(1,s[1].length()-8);
            String fromStr = s[2].substring(0,s[2].length()-5);
            String toStr = s[3].substring(0,s[3].length()-1);

            ArrayList<Integer> temp = (new Gson().fromJson(toStr, new TypeToken<ArrayList<Integer>>(){}.getType()));
            Integer[] tempArr = temp.toArray(new Integer[temp.size()]);
            int[] to = new int[2];
            to[1] = tempArr[0].intValue();
            to[0] = tempArr[1].intValue();

            ArrayList<Integer> temp2 = (new Gson().fromJson(fromStr, new TypeToken<ArrayList<Integer>>(){}.getType()));
            Integer[] tempArr2 = temp2.toArray(new Integer[temp2.size()]);
            int[] from = new int[2];
            from[1] = tempArr2[0].intValue();
            from[0] = tempArr2[1].intValue();

            Game game = gameService.getGameObj(gameId); //new Gson().fromJson(gameService.getGame(gameId), Game.class);
            System.out.println("from: " +from[0]+" " + from[1]+"\n");
            System.out.println("to: " +to[0]+" " + to[1]+"\n");


            game.board.printToConsole();
            Move playeMove = game.move(from,to, true);
            game.board.printToConsole();

            int[] ai_coords = game.runAI();
            Move aiMove = game.move(new int[]{ai_coords[0], ai_coords[1]}, new int[]{ai_coords[2], ai_coords[3]},false);
            game.board.printToConsole();
            gameService.save(game);

            String moves = "{\"moves\": ["+ new Gson().toJson(playeMove) + ',' +new Gson().toJson(aiMove)+"]}";
            System.out.println("\n"+moves);
            return moves;

    }

}
