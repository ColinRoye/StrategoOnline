package cerulean.hw1.Models.GameComponents;


public class Piece{

    public String type;
    public boolean playerPiece;
    public int value;
    public boolean hidden;
    public int dof; //degree of freedom

    //Constructor
    public Piece(){}

    public Piece(String type,int value,int dof){
        this.type = type;
        this.value = value;
        this.dof = dof;
        this.hidden = true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPlayerPiece() {
        return playerPiece;
    }

    public void setPlayerPiece(boolean playerPiece) {
        this.playerPiece = playerPiece;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getDof() {
        return dof;
    }

    public void setDof(int dof) {
        this.dof = dof;
    }
}