package cerulean.hw1.models.gameComponents;

public class Piece{

    public String type;

    public int value;
    public boolean hidden;
    public int dof; //degree of freedom

    public boolean is_user;

    //Constructor
    public Piece(){}

    public Piece(String type,int value,int dof,boolean is_user){
        this.type = type;
        this.value = value;
        this.dof = dof;
        this.hidden = true;
        this.is_user = is_user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isIs_user() {
        return is_user;
    }

    public void setIs_user(boolean is_user) {
        this.is_user = is_user;
    }
}
