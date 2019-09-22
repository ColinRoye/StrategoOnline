package cerulean.hw1.Api.Models;
import com.google.gson.Gson;
import org.springframework.data.annotation.Id;
import java.util.*;
public class Account {
    @Id
    //public ObjectId _id;
    public String username;
    public String password;
    public double winLoss;
    public ArrayList<String> games;





    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.winLoss = 0;
        this.games = new ArrayList<String>();
    }

    // ObjectId needs to be converted to string

    public double getWinLoss() { return winLoss; }

    public void setWinLoss(double winLoss) { this.winLoss = winLoss; }

    public ArrayList<String> getGames() { return games; }

    public void setgames(ArrayList<String> games) { this.games = games; }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public ArrayList<String> getGameSessions() {
        return games;
    }
}

