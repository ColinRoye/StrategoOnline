package cerulean.hw1.Services;

import cerulean.hw1.Database.GameRepository;
import cerulean.hw1.Models.Game;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class GameService {
    @Autowired private GameRepository gameRepository;

    public String getGames(String username){
        return null;
    }
    public String getGame(String gameId){
        Gson gson = new Gson();
        return gson.toJson(gameRepository.findByGameId(gameId));
    }
    public void save(Game game){
        gameRepository.save(game);
    }

    public void move(String gameId, String to, String from){
    }


}
