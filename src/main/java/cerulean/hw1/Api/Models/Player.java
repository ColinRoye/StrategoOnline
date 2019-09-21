package cerulean.hw1.Api.Models;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import java.util.*;
public class Player {
    @Id
    public ObjectId _id;
    public String name;
    public double winLoss;
    public ArrayList<Integer> prevGames;




    // Constructors
    public Player() {}

    public Player(ObjectId _id, String name) {
        this._id = _id;
        this.name = name;
        this.winLoss = 0;
        this.prevGames = new ArrayList<Integer>();
    }

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }



    public double getWinLoss() { return winLoss; }

    public void setWinLoss(double winLoss) { this.winLoss = winLoss; }

    public ArrayList<Integer> getPrevGames() { return prevGames; }

    public void setPrevGames(ArrayList<Integer> prevGames) { this.prevGames = prevGames; }
}

