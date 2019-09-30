package cerulean.hw1.models;
import cerulean.hw1.models.gameComponents.Board;


import cerulean.hw1.models.gameComponents.Move;
import cerulean.hw1.models.gameComponents.Piece;
import com.google.gson.Gson;

import java.util.ArrayList;


//For Stratego rules https://en.wikipedia.org/wiki/Stratego


import java.util.*;


public class Game{

    public String gameId;

    public Account player;
    public int moveCounter;
    public String winner;
    public Board board;
    public Map<Integer, Integer> totalPiece_table = new Hashtable<>();
    public Map<Integer, Integer> foundPiece_table = new Hashtable<>();
    public ArrayList<Move> moves = new ArrayList<Move>();

    public static final String WON = "WON";
    public static final String LOST = "LOST";
    public static final String MOVED = "MOVED";
    public static final String DRAW = "DRAW";
    public static final String PLAYER_WIN = "PLAYER";
    public static final String AI_WIN = "COMPUTER";


    //Constructor
    public Game(){}

    public Game(String gameId){
        this.gameId = gameId;
        //this.player = player;
        this.moveCounter = 0;

        this.board = new Board(10,10);

        //Table for all possible pieces
        this.totalPiece_table.put(0,1);
        this.totalPiece_table.put(1,1);
        this.totalPiece_table.put(2,8);
        this.totalPiece_table.put(3,5);
        this.totalPiece_table.put(4,4);
        this.totalPiece_table.put(5,4);
        this.totalPiece_table.put(6,4);
        this.totalPiece_table.put(7,3);
        this.totalPiece_table.put(8,2);
        this.totalPiece_table.put(9,1);
        this.totalPiece_table.put(10,1);
        this.totalPiece_table.put(11,6);

        //Table for all pieces that have been found
        this.foundPiece_table.put(0,0);
        this.foundPiece_table.put(1,0);
        this.foundPiece_table.put(2,0);
        this.foundPiece_table.put(3,0);
        this.foundPiece_table.put(4,0);
        this.foundPiece_table.put(5,0);
        this.foundPiece_table.put(6,0);
        this.foundPiece_table.put(7,0);
        this.foundPiece_table.put(8,0);
        this.foundPiece_table.put(9,0);
        this.foundPiece_table.put(10,0);
        this.foundPiece_table.put(11,0);
    }
    /*
    public String move(Account account, String gameSession, int[] moveFrom, int[] moveTo){
        String result = makeMove(account, gameSession, moveFrom, moveTo,true);

        int[] ai_move = runAI();
        moveFrom[0] = ai_move[0];
        moveFrom[1] = ai_move[1];

        moveTo[0] = ai_move[2];
        moveTo[1] = ai_move[3];
        String ai_result = makeMove(account,gameSession,moveFrom,moveTo,false);
        System.out.printf("AI MOVED FROM %d,%d TO %d,%d \n ",moveFrom[0],moveFrom[1],moveTo[0],moveTo[1]);
        return result;

    }*/


    public Move move(int[] moveFrom, int[] moveTo,boolean is_player) throws Exception{

        Move move = new Move();

        int i = moveFrom[0];
        int j = moveFrom[1];
        move.setFrom(new int[]{i, j});

        int x = moveTo[0];
        int y = moveTo[1];
        move.setTo(new int[]{x, y});

        if(is_player)
            move.setActor("PLAYER");
        else
            move.setActor("COMPUTER");


        //Check to see if piece if being moved into inaccessible part of board
        if(!validateRegion(x,y))
            throw new Exception("INACCESSIBLE PART OF BOARD");

        Piece moveFrom_piece = this.board.getBoard_piece(i,j);
        move = setSubjectOrTarget(move,moveFrom_piece,"Subject");//move.setSubject(moveFrom_piece.getType());

        Piece moveTo_piece = this.board.getBoard_piece(x,y);
        if(moveTo_piece != null)
            move = setSubjectOrTarget(move,moveTo_piece,"Target"); //move.setTarget(moveTo_piece.getType());
        else
            move.setTarget(null);

        //Check if moveFrom_piece is not null
        if(moveFrom_piece == null)
            throw new Exception("Chosen Piece is Null");

        //Check if move is inside P DOF
        int dof = moveFrom_piece.getDof();
        double desiredDistance = Math.abs(Math.hypot(x-i,y-j));

        if(desiredDistance > dof || (desiredDistance != Math.floor(desiredDistance)))
            throw new Exception("Invalid move RE: dof");

        //Running into your own piece
        if(moveTo_piece != null && (moveFrom_piece.isIs_user() == moveTo_piece.isIs_user()) ){
            throw new Exception("RUNNING INTO SAME USER PIECE");
        }
        //Moving to an empty space
        else if(moveTo_piece == null){
            //Set board piece
            this.board.setBoard_piece(x,y,moveFrom_piece);
            //set current position to null
            this.board.setBoard_piece(i,j,null);
            move.setResult(MOVED);


        }
        else{
            String battleResult = battle(moveFrom_piece,moveTo_piece);

            if(battleResult.equals("FLAG CAPTURED")){
                if(is_player){
                    this.winner = PLAYER_WIN;
                }
                else{
                    this.winner = AI_WIN;
                }
                move.setResult(WON);
            }
            else if(battleResult.equals(WON)){  //First Piece won the battle
                this.board.setBoard_piece(x,y,moveFrom_piece);
                this.board.setBoard_piece(i,j,null);
                moveFrom_piece.setHidden(false);

                if(moveFrom_piece.is_user == is_player) {
                    int currentFound = foundPiece_table.get(moveFrom_piece.value);
                    foundPiece_table.put(moveFrom_piece.value, ++currentFound);
                    this.board.totalPieces -= 1;
                }

                move.setResult(WON);
            }
            else if(battleResult.equals(LOST)){ //Second Piece won the battle
                this.board.setBoard_piece(i,j,null);
                moveTo_piece.setHidden(false);

                //Player lost , AI won , moveFrom piece was player is now known
                if (moveTo_piece.isIs_user() == is_player) {
                    int currentFound = foundPiece_table.get(moveFrom_piece.value);
                    foundPiece_table.put(moveFrom_piece.value, ++currentFound);
                    totalPiece_table.put(moveFrom_piece.value, totalPiece_table.get(moveFrom_piece.value) - 1);
                    this.board.totalPieces -= 1;
                }
                //AI Lost, Player won, moveTo piece is now revealed to AI
                else{
                    int currentFound = foundPiece_table.get(moveTo_piece.value);
                    foundPiece_table.put(moveTo_piece.value, ++currentFound);
                    totalPiece_table.put(moveTo_piece.value, totalPiece_table.get(moveTo_piece.value) - 1);
                }

                move.setResult(LOST);


            }
            else{// Tie
                this.board.setBoard_piece(i,j,null);
                this.board.setBoard_piece(x,y,null);
                int currentFound = foundPiece_table.get(moveFrom_piece.value);
                foundPiece_table.put(moveFrom_piece.value,++currentFound);
                totalPiece_table.put(moveFrom_piece.value,totalPiece_table.get(moveFrom_piece.value) - 1);
                this.board.totalPieces -= 1;

                move.setResult(DRAW);
            }
        }
        this.moveCounter++;
        move.setMoveIndex(moveCounter);
        moves.add(move);

        return move;
    }


    public int[] runAI(){

        Piece[][] board = this.board.getBoard_pieces();
        int[] result = new int[4];
        List<int[]> potential = new ArrayList<>();



        Piece p_above = null;
        Piece p_below = null;
        Piece p_right = null;//new Piece("EMPTY",-1,0,false);
        Piece p_left = null; //new Piece("EMPTY",-1,0,false);

        /* Check board for empty spaces
           if empty space is found, look for AI piece around it
         */
        //TODO: Switch from looking for empty space to looking all pieces and looking to see if empty space is there as well enemy piece
        for(int i = 0 ; i < this.board.num_row ;i++){
            for(int j = 0; j< this.board.num_col;j++){
                Piece p = board[i][j];
                if(p == null && validateRegion(i,j)){
                    //Check to see if any usable piece that exists around this empty square
                    if(i-1 > -1 && validateRegion(i-1,j))
                        p_above = board[i-1][j];
                    if(i+1 < 10 && validateRegion(i+1,j))
                        p_below = board[i+1][j];
                    if(j-1 > -1 && validateRegion(i,j-1))
                        p_left = board[i][j-1];
                    if(j+1 < 9 && validateRegion(i,j+1))
                        p_right = board[i][j+1];

                    int temp[] = new int[2];


                    if(p_above != null && !p_above.is_user) {
                        temp[0] = i-1;
                        temp[1] = j;
                        if(!p_above.getType().equals("Bomb"));
                            potential.add(temp.clone());
                    }
                    if(p_below != null && !p_below.is_user) {
                        temp[0] = i+1;
                        temp[1] = j;
                        if(!p_below.getType().equals("Bomb"));
                            potential.add(temp.clone());
                    }
                    if(p_left != null && !p_left.is_user) {
                        temp[0] = i;
                        temp[1] = j-1;

                        if(!p_left.getType().equals("Bomb"));
                            potential.add(temp.clone());
                    }
                    if(p_right != null && !p_right.is_user) {
                        temp[0] = i;
                        temp[1] = j+1;
                        if(!p_right.getType().equals("Bomb"));
                            potential.add(temp.clone());
                    }

                    p_above = null;
                    p_below = null;
                    p_right = null;
                    p_left = null;
                    temp=null;
                }
            }
        }
        //Now there is a list of potential pieces that can be moved


        int numberOfOptions = potential.size();
        //Look for enemy pieces around potential pieces
        outerloop:
        for(int k = 0; k < numberOfOptions; k++){
            int[] opt = potential.get(k);
            int x = opt[0];
            int y = opt[1];
            Piece attack_piece = this.board.getBoard_piece(x,y);

            if(attack_piece.getType().equals("Bomb"))
                continue;

            //Check to see if there are opponent pieces around
            Piece test_piece;
            /*
             Looking at all pieces within a 2 block radius
             X X 0 X X
             X 0 0 0 X
             0 0 P 0 0
             X 0 0 0 X
             X X 0 X X
            */

            int r = 2;
            double r2 = Math.pow(r,2);

            /*For Testing
            x = 4;
            y = 4;
            */

            //System.out.printf("----POINT %d, %d\n",x,y);
            for (int i = x-r; i <= x+r; i++) {
                int xr = (int) Math.pow(i-x,2);
                for (int j = y-r; j <= y+r; j++) {
                    int check = (int) (xr + (Math.pow((y-j),2)));

                    //ignore test point
                    if((i == x && j == y ) || !validateRegion(x,y))
                        continue;
                    //Check if within 2 block radius
                    if(check <= r2 && validateRegion(i,j)){
                        //Now check if this point is an enemy point
                        test_piece = this.board.getBoard_piece(i,j);

                        if(test_piece != null && test_piece.is_user){
                            //At this point we have a target piece to either attack or runaway
                            System.out.printf("ENEMY FOUND AT %d,%d \n",i,j);
                            int[] moveTo = null;

                            if (!test_piece.isHidden() && !attack_piece.getType().equals("Bomb")) {

                                if (battle(attack_piece, test_piece) == WON ) {
                                    moveTo = moveToward(attack_piece, x, y, i, j);
                                }
                                else
                                    moveTo = moveAway(attack_piece, x, y, i, j);
                            }
                            else {

                                double prob_smaller = probabilityEqualOrSmaller(attack_piece.getValue());
                                if (prob_smaller == 0 || (prob_smaller > (1 - prob_smaller)))
                                    moveTo = moveToward(attack_piece, x, y, i, j);
                                else if ((1 - prob_smaller) > prob_smaller)
                                    moveTo = moveAway(attack_piece, x, y, i, j);
                                else
                                    moveTo = moveToward(attack_piece, x, y, i, j);
                            }

                            if (moveTo != null) {
                                result[0] = x;
                                result[1] = y;
                                result[2] = moveTo[0];
                                result[3] = moveTo[1];
                                return result;
                            }
                        }
                        else
                            continue;
                    }
                }
            }
            //Now there is a list of potential pieces that can be moved
        }


        //If no enemies were found make a random move
        int choice = new Random().nextInt(numberOfOptions);
        int[] opt = potential.get(choice);

        result[0] = opt[0];
        result[1] = opt[1];

        //Look around this point and move to an empty spot
        //below
        if(validateRegion(result[0]+1,result[1])){
            Piece p = this.board.getBoard_piece(result[0]+1,result[1]);
            if(p == null){
                result[2] = result[0]+1;
                result[3] = result[1];
                return result;
            }
        }
        //left
        if(validateRegion(result[0],result[1]-1)){
            Piece p = this.board.getBoard_piece(result[0],result[1]-1);
            if(p == null){
                result[2] = result[0];
                result[3] = result[1]-1;
                return result;
            }
        }
        //right
        if(validateRegion(result[0],result[1])){
            Piece p = this.board.getBoard_piece(result[0],result[1]+1);
            if(p == null){
                result[2] = result[0];
                result[3] = result[1]+1;
                return result;
            }
        }
        //up
        if(validateRegion(result[0]-1,result[1])){
            Piece p = this.board.getBoard_piece(result[0]-1,result[1]);
            if(p == null){
                result[2] = result[0]-1;
                result[3] = result[1];
                return result;
            }
        }
        return result;
    }

    public int[] moveToward(Piece attack, int x1,int y1,int x2,int y2){

        //We can move either forwad,back,left,or right
        int dof = attack.getDof();

        int[] result = new int[2];


        //first check if we are within the dof

        if(((Math.abs(x1-x2) <= dof && Math.abs(y1-y2) == 0 )||((Math.abs(x1-x2) == 0 && Math.abs(y1-y2) <= dof ))  )){
            result[0] = x2;
            result[1] = y2;
            return result;
        }
        double[] option_dist = new double[4];
        double currentDistanceAway = distanceAway(x1,y1,x2,y2);
        option_dist[0] = distanceAway(x1+1,y1,x2,y2);//down
        option_dist[1] = distanceAway(x1-1,y1,x2,y2);//up
        option_dist[2] = distanceAway(x1,y1-1,x2,y2);//left
        option_dist[3] = distanceAway(x1,y1+1,x2,y2);//right

        //Not in dof, so we need to move toward the piece
        Piece option_below;
        Piece option_above;
        Piece option_right;
        Piece option_left;
        //down
        if(validateRegion(x1+1,y1)) {
            option_below = this.board.getBoard_piece(x1 + 1, y1);
            if(option_below == null && option_dist[0] <currentDistanceAway){
                result[0] = x1+1;
                result[1] = y1;
                return result;
            }
        }
        //up
        if(validateRegion(x1-1,y1)) {
            option_above = this.board.getBoard_piece(x1 - 1, y1);
            if(option_above == null && option_dist[1] <currentDistanceAway){
                result[0] = x1-1;
                result[1] = y1;
                return result;
            }
        }
        //left
        if(validateRegion(x1,y1-1)){
            option_left = this.board.getBoard_piece(x1,y1-1);
            if(option_left == null && option_dist[2] <currentDistanceAway){
                result[0] = x1;
                result[1] = y1-1;
                return result;
            }}
        //right
        if(validateRegion(x1,y1+1)){
            option_right = this.board.getBoard_piece(x1,y1+1);
            if(option_right == null && option_dist[3] <currentDistanceAway){
                result[0] = x1;
                result[1] = y1+1;
                return result;
            }
        }
        return null;
    }
    public int[] moveAway(Piece piece, int x1,int y1,int x2,int y2){
        //We can move either forwad,back,left,or right
        int dof = piece.getDof();

        int[] result = new int[2];

        double[] option_dist = new double[4];
        double currentDistanceAway = distanceAway(x1,y1,x2,y2);
        option_dist[0] = distanceAway(x1+1,y1,x2,y2); // down
        option_dist[1] = distanceAway(x1-1,y1,x2,y2); // up
        option_dist[2] = distanceAway(x1,y1-1,x2,y2); // left
        option_dist[3] = distanceAway(x1,y1+1,x2,y2); // right

        //Not in dof, so we need to move toward the piece
        Piece option_below;
        Piece option_above;
        Piece option_right;
        Piece option_left;

        //go left
        if(validateRegion(x1,y1-1)){
            option_left = this.board.getBoard_piece(x1,y1-1);
            if(option_left == null && option_dist[2] > currentDistanceAway){
                result[0] = x1;
                result[1] = y1-1;
                return result;
            }}
        //go right
        if(validateRegion(x1,y1+1)){
            option_right = this.board.getBoard_piece(x1,y1+1);
            if(option_right == null && option_dist[3] > currentDistanceAway){
                result[0] = x1;
                result[1] = y1+1;
                return result;
            }
        }

        //go down
        if(validateRegion(x1+1,y1)) {
            option_below = this.board.getBoard_piece(x1 + 1, y1);
            if(option_below == null && option_dist[0] > currentDistanceAway){
                result[0] = x1+1;
                result[1] = y1;
                return result;
            }
        }
        //go up
        if(validateRegion(x1-1,y1)) {
            option_above = this.board.getBoard_piece(x1 - 1, y1);
            if(option_above == null && option_dist[1]  > currentDistanceAway){
                result[0] = x1-1;
                result[1] = y1;
                return result;
            }
        }
        return null;
    }
    public double distanceAway(int x1,int y1,int x2,int y2){
        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2 - x1);

        return Math.hypot(ac, cb);

    }


    public boolean validateRegion(int x,int y){
        //Outside of board
        if( x < 0 || x > 9 || y > 9 || y < 0)
            return false;
        //inaccessible region
        if((x > 3 && x < 6 ) && ((y>1 && y < 4) || (y>5 && y<8) ) )
            return false;
        else
            return true;
    }
    public boolean validateMove(int x1, int y1,int x2,int y2){

        //Trying to go into invalid region
        if((x2 > 3 && x2 < 6 ) && ((y2>1 && y2 < 4) || (y2>5 && y2<8) ) )
            return false;

        Piece myPiece = this.board.getBoard_piece(x1,y1);
        Piece moveTo_piece = this.board.getBoard_piece(x2,y2);
        if(myPiece == null)
            return false;
        if(moveTo_piece == null)
            return true;
        //Are you trying to move into your own piece
        if(myPiece.is_user == moveTo_piece.is_user)
            return false;

        return true;

    }
    public double probabilityEqualOrSmaller(int k){
        int counter = 0;
        for(int i = k; k > -1 ; k--){
            counter += (totalPiece_table.get(i)-foundPiece_table.get(i));
        }
        //Exceptions
        if(k == 1){
            counter += (totalPiece_table.get(10)-foundPiece_table.get(10)); //Spy can defeat Marshal(10)
        }

        if(k == 3){
            counter += (totalPiece_table.get(11)-foundPiece_table.get(11)); //Miner can defeat bomb
        }
        double result = (double) counter / this.board.totalPieces ;
        return result;

    }


    public String battle(Piece p1, Piece p2){

        String p1_win = WON;//p1
        String p2_win = LOST;//p2
        String draw = DRAW;
        //First Let's get attacking piece type
        String p1_type = p1.getType();
        String p2_type = p2.getType();

        if(p2_type.equals("Flag")){
            return "FLAG CAPTURED";
        }

        //Exceptions
        //Spy attacks Miner or Flag
        if(p1_type.equals("Spy") && (p2_type.equals("Marshal") || p2_type.equals("Flag"))) {
            System.out.println("SPY DEFEATED Marshal | FLAG ");
            return p1_win;
        }
        //Miner Attacks Bomb
        else if(p1_type.equals("Miner") && p2_type.equals("Bomb")){
            System.out.println("MINER DEFEATED BOMB");
            return p1_win;
        }
        else if(p2_type.equals("BOMB")) {
            System.out.println("LOST TO BOMB");
            return p2_win;
        }

        int p1_value = p1.value;
        int p2_value = p2.value;
        if(p1_value > p2_value) {
            System.out.printf("P1 %d beat P2 %d  \n",p1_value,p2_value);
            return p1_win;
        }
        else if(p2_value > p1_value) {
            System.out.printf("P1 %d lost to  P2 %d  \n",p1_value,p2_value);
            return p2_win;
        }
        else {
            System.out.println("DRAW");
            return draw;
        }
    }

    public Move setSubjectOrTarget(Move m, Piece p ,String type){
        String output = "";
        if(p.getType() == "Bomb")
            output = "B";
        else if(p.getType() == "Flag")
            output = "F";
        else if(p.getType() == "Spy")
            output = "S";
        else
            output = Integer.toString(p.getValue());
        if(type == "Subject")
            m.setSubject(output);
        else
            m.setTarget(output);
        return m;

    }


    public String getsessionId() { return gameId; }
    public void setsessionId(String sessionId) { this.gameId = sessionId; }

    public Account getPlayer() {
        return player;
    }

    public void setPlayer(Account player) {
        this.player = player;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getGameId() {
        return gameId;
    }
}