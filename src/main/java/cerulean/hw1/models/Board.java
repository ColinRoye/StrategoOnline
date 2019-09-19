package cerulean.hw1.models;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import java.util.*;
import cerulean.hw1.models.Piece;

public class Board{

    public Piece[][] board_pieces;

    public Board(){}

    public Board(int i, int j) {
        this.board_pieces = new Piece[i][j];
    }

    public Piece[][] getBoard_pieces() {
        return board_pieces;
    }

    public void setBoard_pieces(Piece[][] board_pieces) {
        this.board_pieces = board_pieces;
    }
}