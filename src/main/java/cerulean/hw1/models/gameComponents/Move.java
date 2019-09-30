package cerulean.hw1.models.gameComponents;

public class Move {
    String actor;
    String subject;
    String target;
    String result;
    int[] from;
    int[] to;
    int moveIndex;



    public Move(String actor, String subject, String target, int[] from, int[] to, String result,int moveIndex){
        this.actor = actor;
        this.subject = subject;
        this.target = target;
        this.from = from;
        this.to = to;
        this.result = result;
        this.moveIndex = moveIndex;

    }

    public Move() {

    }
    public void setMoveIndex(int moveIndex){
        this.moveIndex = moveIndex;
    }
    public int getMoveIndex(){
        return this.moveIndex;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setFrom(int[] from) {
        this.from = from;
    }

    public void setTo(int[] to) {
        this.to = to;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getActor() {
        return actor;
    }

    public String getSubject() {
        return subject;
    }

    public String getTarget() {
        return target;
    }

    public int[] getFrom() {
        return from;
    }

    public int[] getTo() {
        return to;
    }

    public String getResult() {
        return result;
    }



}
//    “game_id”: game move is being made to
//            “actor”: AI or player that made the move?
//            “from”: location of the piece being moved
//            “to”: location the piece wants to move to
//            “subject”: piece that is being moved (can be HIDDEN for AI moves)
//            “target”: Type of piece at the destination (or null if empty space)
//            “result”: enum that could have the following values “MOVED”, “WON”, “LOST”,
