package cerulean.hw1.Models.GameComponents;

import java.util.ArrayList;

public class Board{

    Piece[][] board_pieces;
    public Board(){}

    public Board(int i, int j) {
        this.board_pieces = new Piece[i][j];
        board_pieces[0][0] = new Piece("test", 2, 4);
    }

    public Piece[][] getBoard_pieces() {
        return board_pieces;
    }

    public void setBoard_pieces(Piece[][] board_pieces) {
        this.board_pieces = board_pieces;
    }
}