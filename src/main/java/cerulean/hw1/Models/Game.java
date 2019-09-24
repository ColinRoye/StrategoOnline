package cerulean.hw1.Models;
import cerulean.hw1.Models.GameComponents.Board;

import com.google.gson.Gson;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.parameters.P;
import java.util.UUID;


public class Game{

    @Id
    private String gameId;

    private String username;
    private int moveCounter;
    private int winner;
    private Board board;

    //Constructor
    public Game(){}

    public Game(String username){
        UUID randId = UUID.randomUUID();

        this.gameId = randId.toString();
        this.username = username;
        this.moveCounter = 0;
        this.winner = 0;
        this.board = new Board(); //Board Size Needs to be set!
    }
    //TODO: implement move
    public void move(int from, int to){
//        if(isValidMove(from, to)){
//            moveCounter++;
//        }
    }



    public String getGameId() {
        return gameId;
    }
    public String getPlayer() {
        return username;
    }
    public int getMoveCounter() {
        return moveCounter;
    }
    // NO NEED FOR SET USERNAME, game username will never change
//    public void setUsername(Account player) {
//        this.username = username;
//    }
    // NO MOVE COUNTER SET, should only change when move is called
//    public void setMoveCounter(int moveCounter) {
//        this.moveCounter = moveCounter;
//    }

    public int getWinner() {
        return winner;
    }
    public void setWinner(int winner) {
        this.winner = winner;
    }

    public Board getBoard() {
        return board;
    }

//    MOVE WILL ONLY CHANGE BOARD
//    public void setBoard(Board board) {
//        this.board = board;
//    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}