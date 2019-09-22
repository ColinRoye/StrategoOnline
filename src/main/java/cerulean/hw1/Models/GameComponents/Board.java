package cerulean.hw1.Models.GameComponents;

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