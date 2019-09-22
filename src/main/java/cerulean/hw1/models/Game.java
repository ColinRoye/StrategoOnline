package cerulean.hw1.models;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import java.util.*;

import cerulean.hw1.models.Board;
import cerulean.hw1.models.Player;



public class Game{

    @Id
    public ObjectId _id;

    public Player player;
    public int moveCounter;
    public int winner;
    public Board board;

    //Constructor
    public Game(){}

    public Game(ObjectId _id,Player player){
        this._id = _id;
        this.player = player;
        this.moveCounter = 0;
        this.winner = 0;
        this.board = new Board(); //Board Size Needs to be set!
    }

    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}