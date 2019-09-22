package cerulean.hw1.Api.Models.Account;
import com.google.gson.Gson;
import org.springframework.data.annotation.Id;
import java.util.*;
public class Account {
    @Id
    //public ObjectId _id;
    public String username;
    public String password;
    public double winLoss;
    public ArrayList<Integer> prevGames;





    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.winLoss = 0;
        this.prevGames = new ArrayList<Integer>();
    }

    // ObjectId needs to be converted to string

    public double getWinLoss() { return winLoss; }

    public void setWinLoss(double winLoss) { this.winLoss = winLoss; }

    public ArrayList<Integer> getPrevGames() { return prevGames; }

    public void setPrevGames(ArrayList<Integer> prevGames) { this.prevGames = prevGames; }
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
}

