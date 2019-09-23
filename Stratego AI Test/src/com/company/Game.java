package com.company;

//For Stratego rules https://en.wikipedia.org/wiki/Stratego


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Game{

    public String gameId;

    public Account player;
    public int moveCounter;
    public int winner;
    public Board board;
    public Dictionary totalPiece_table = new Hashtable();
    public Dictionary foundPiece_table = new Hashtable();
    //Constructor
    public Game(){}

    public Game(String sessionId, Account player){
        this.gameId = sessionId;
        this.player = player;
        this.moveCounter = 0;
        this.winner = 0;
        this.board = new Board(10,10); //Board Size Needs to be set!

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

    public int move(Account account, String gameSession, int[] moveFrom, int[] moveTo) throws Exception{

        int i = moveFrom[0];
        int j = moveFrom[1];

        int x = moveTo[0];
        int y = moveTo[1];

        //Move into inaccessible part of board
        if((x > 3 && x < 6 ) && ((y>1 && y < 4) || (y>5 && y<8) ) )
            throw new Exception("INACCESSIBLE PART OF BOARD");


        //get piece at moveFrom
        Piece player_piece = this.board.getBoard_piece(i,j);
        Piece moveTo_piece = this.board.getBoard_piece(x,y);

        //Check if player_piece is not null
        if(player_piece == null)
            throw new Exception("Chosen Piece is Null");

        //Check if move is inside P DOF
        int dof = player_piece.getDof();
        double desiredDistance = Math.abs(Math.hypot(x-i,y-j));

        if(desiredDistance > dof || (desiredDistance != Math.floor(desiredDistance)))
            throw new Exception("Invalid move RE: dof");


        if(moveTo_piece!= null && (!player_piece.isIs_user() || moveTo_piece.isIs_user()) ){
            throw new Exception("MOVE FROM IS NOT PLAYER PIECE OR MOVE TO IS PLAYER PIECE RE: dof");
        }
        else if(moveTo_piece == null){
            //Set board piece
            this.board.setBoard_piece(x,y,player_piece);
            //set current position to null
            this.board.setBoard_piece(i,j,null);
        }
        else{
            String battleResult = battle(player_piece,moveTo_piece);

            if(battleResult.equals("P1")){
                this.board.setBoard_piece(x,y,player_piece);
                this.board.setBoard_piece(i,j,null);
                player_piece.setHidden(false);

                int currentFound = (int) foundPiece_table.get(player_piece.value);
                foundPiece_table.put(player_piece.value,++currentFound);
                this.board.totalPieces -= 1;

            }
            else if(battleResult.equals("P2")){
                this.board.setBoard_piece(i,j,null);
                moveTo_piece.setHidden(false);
                int currentFound = (int) foundPiece_table.get(player_piece.value);
                foundPiece_table.put(player_piece.value,++currentFound);

                totalPiece_table.put(player_piece.value,(int)totalPiece_table.get(player_piece.value) - 1);
                this.board.totalPieces -= 1;

            }
            else{
                this.board.setBoard_piece(i,j,null);
                this.board.setBoard_piece(x,y,null);
                int currentFound = (int) foundPiece_table.get(player_piece.value);
                foundPiece_table.put(player_piece.value,++currentFound);
                totalPiece_table.put(player_piece.value,(int)totalPiece_table.get(player_piece.value) - 1);
                this.board.totalPieces -= 2;
            }
        }
        this.moveCounter++;
        //int[] airesult = runAI();

        return 0;
    }


    public int[] runAI(){

        Piece board[][] = this.board.getBoard_pieces();
        int[] result = new int[2];

        List<int[]> potential = new ArrayList<>();


        //First find the closest opponent piece

        //If closest opponent distance == 1
        //Get Probability smaller, prob bigger
        //See which one is closer to 1 and based on that
        //Either run away or attack
        Piece p_above = new Piece("EMPTY",-1,0,false);
        Piece p_below = new Piece("EMPTY",-1,0,false);
        Piece p_right = new Piece("EMPTY",-1,0,false);
        Piece p_left = new Piece("EMPTY",-1,0,false);

        for(int i = 0 ; i < this.board.num_row;i++){
            for(int j = 0; j< this.board.num_col;j++){
              Piece p = board[i][j];
              if(p == null){
                  //Check to see if any usable piece exists around this square

                  if(i-1>=0 && validateRegion(i-1,j))
                      p_above = board[i-1][j];
                  if(i+1 < 10 && validateRegion(i+1,j))
                      p_below = board[i+1][j];
                  if(j-1 >= 0 && validateRegion(i,j-1))
                      p_left = board[i][j-1];
                  if(j+1 < 9 && validateRegion(i,j+1))
                      p_right = board[i][j+1];

                  int temp[] = new int[2];
                  if(p_above != null && !p_above.is_user && !p_above.type.equalsIgnoreCase("Empty")) {
                      temp[0] = i-1;
                      temp[1] = j;
                      potential.add(temp);
                  }
                  if(p_below != null && !p_below.is_user && !p_below.type.equalsIgnoreCase("Empty")) {
                      temp[0] = i+1;
                      temp[1] = j;
                      potential.add(temp);
                  }
                  if(p_left != null && !p_left.is_user && !p_left.type.equalsIgnoreCase("Empty")) {
                      temp[0] = i;
                      temp[1] = j-1;
                      potential.add(temp);
                  }
                  if(p_right != null && !p_right.is_user && !p_left.type.equalsIgnoreCase("Empty")) {
                      temp[0] = i;
                      temp[1] = j+1;
                      potential.add(temp);
                  }
              }

              //Now there is a list of potential pieces that can be moved



            }
        }










        return result;


    }
    public boolean validateRegion(int x,int y){

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
        if(myPiece.is_user== moveTo_piece.is_user)
            return false;

        return true;



    }
    public double probabilityEqualOrSmaller(int k){
        int counter = 0;
        for(int i = k; k > -1 ; k--){
            counter += (int)foundPiece_table.get(i);
        }

        return counter/this.board.totalPieces;



    }


    public String battle(Piece p1, Piece p2){

        String p1_win = "P1";
        String p2_win = "P2";
        String draw = "Draw";
        //First Let's get attacking piece type
        String p1_type = p1.getType();
        String p2_type = p2.getType();

        //Exceptions
        //Spy attacks Miner or Flag
        if(p1_type == "Spy" && (p2_type == "Miner" || p2_type == "Flag")) {
            System.out.println("SPY DEFEATED MINER | FLAG ");
            return p1_win;
        }
        //Miner Attacks Bomb
        else if(p1_type == "Miner" && p2_type == "Bomb"){
            System.out.println("MINER DEFEATED BOMB");
            return p1_win;
        }
        else if(p2_type == "BOMB") {
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
            System.out.print("DRAW \n");
            return draw;
        }


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