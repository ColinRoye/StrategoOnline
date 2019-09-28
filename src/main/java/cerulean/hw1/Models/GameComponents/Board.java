package cerulean.hw1.Models.GameComponents;


import java.util.ArrayList;

public class Board{

    public Piece[][] board_pieces;
    public int num_row;
    public int num_col;
    public int totalPieces = 40; //player pieces

    public Board(){}

    public Board(int i, int j) {

        this.board_pieces = new Piece[i][j];
        this.num_col = j;
        this.num_row = i;
    }

    public Piece[][] getBoard_pieces() {
        return board_pieces;
    }

    public void setBoard_pieces(Piece[][] board_pieces) {
        this.board_pieces = board_pieces;
    }
    public void setBoard_piece(int i, int j, Piece p){
        this.board_pieces[i][j] = p;

    }
    public Piece getBoard_piece(int i, int j){
        return this.board_pieces[i][j];

    }
    public void swap(int[] p1_coord,int[] p2_coord){
        Piece p1 = this.getBoard_piece(p1_coord[0],p1_coord[1]);
        Piece p2 = this.getBoard_piece(p2_coord[0],p2_coord[1]);
        this.setBoard_piece(p1_coord[0],p1_coord[1],p2);
        this.setBoard_piece(p2_coord[0],p2_coord[1],p1);
    }
    public void postBoard(ArrayList<Piece> result){

        //Player Pieces are rows 6 -> 9

        int counter = 0;
        for(int i = 6; i < 10; i++){
            for(int j = 0; j < 9; j++){
                this.setBoard_piece(i,j,result.get(counter));
                counter++;
            }
        }



    public void printToConsole(){
        Piece p;
        System.out.printf("    ");
        for(int j = 0;j<this.num_col;j++){
            System.out.printf(" %c%-3d ",'A'+j,j);
        }
        System.out.print("\n");

        for(int i = 0 ; i<this.num_row;i++){
            System.out.printf(" R%d ",i);
            for(int j = 0 ; j<this.num_col;j++){

                if((i > 3 && i < 6 ) && ((j>1 && j < 4) || (j>5 && j<8) ) )
                    System.out.printf(" %4s ","|X|");
                else {
                    p = this.board_pieces[i][j];
                    if (p == null)
                        System.out.printf(" %4s ", " - ");
                    else
                        System.out.printf(" %s%2d%s ",(p.is_user ? "P":"C"), p.getValue(),(p.isHidden() ?"H":"E"));
                }
            }
            System.out.print("\n");
        }
    }

    /*
    (0,0) -> (3,9) AI
    (0,6) -> (9,9) Player

    i = 9 for PLAYER
    i = 3 for AI
    */
    public void setDefaultBoard(boolean is_user,int i){
        //new Piece("Flag",0,0,true)
        //new Piece("Spy",1,1,true)
        //new Piece("Scout",2,10,true)
        //new Piece("Miner",3,1,true)
        //new Piece("Sergeant",4,1,true)
        //new Piece("Lieutenant",5,1,true)
        //new Piece("Captain",6,1,true)
        //new Piece("Major",7,1,true)
        //new Piece("Colonel",8,1,true)
        //new Piece("General",9,1,true)
        //new Piece("Marshal",10,1,true)
        //new Piece("Bomb",11,0,true)
        int increment = -1;
        if(is_user == false)
            increment = 1;

        //Player Pieces
        Piece BOMB_1 = new Piece("Bomb",11,0,is_user);
        Piece BOMB_2 = new Piece("Bomb",11,0,is_user);
        Piece BOMB_3 = new Piece("Bomb",11,0,is_user);
        Piece BOMB_4 = new Piece("Bomb",11,0,is_user);
        Piece BOMB_5 = new Piece("Bomb",11,0,is_user);
        Piece BOMB_6 = new Piece("Bomb",11,0,is_user);

        Piece MARSHAL_1 = new Piece("Marshal",10,1,is_user);

        Piece GENERAL_1 = new Piece("General",9,1,is_user);

        Piece COLONEL_1 = new Piece("Colonel",8,1,is_user);
        Piece COLONEL_2 = new Piece("Colonel",8,1,is_user);

        Piece MAJOR_1 = new Piece("Major",7,1,is_user);
        Piece MAJOR_2 = new Piece("Major",7,1,is_user);
        Piece MAJOR_3 = new Piece("Major",7,1,is_user);

        Piece CAPTAIN_1 = new Piece("Captain",6,1,is_user);
        Piece CAPTAIN_2 = new Piece("Captain",6,1,is_user);
        Piece CAPTAIN_3 = new Piece("Captain",6,1,is_user);
        Piece CAPTAIN_4 = new Piece("Captain",6,1,is_user);

        Piece LIEUTENANT_1 = new Piece("Lieutenant",5,1,is_user);
        Piece LIEUTENANT_2 = new Piece("Lieutenant",5,1,is_user);
        Piece LIEUTENANT_3 = new Piece("Lieutenant",5,1,is_user);
        Piece LIEUTENANT_4 = new Piece("Lieutenant",5,1,is_user);

        Piece SERGEANT_1 = new Piece("Sergeant",4,1,is_user);
        Piece SERGEANT_2 = new Piece("Sergeant",4,1,is_user);
        Piece SERGEANT_3 = new Piece("Sergeant",4,1,is_user);
        Piece SERGEANT_4 = new Piece("Sergeant",4,1,is_user);

        Piece MINER_1 = new Piece("Miner",3,1,is_user);
        Piece MINER_2 = new Piece("Miner",3,1,is_user);
        Piece MINER_3 = new Piece("Miner",3,1,is_user);
        Piece MINER_4 = new Piece("Miner",3,1,is_user);
        Piece MINER_5 = new Piece("Miner",3,1,is_user);

        Piece SCOUT_1 = new Piece("Scout",2,10,is_user);
        Piece SCOUT_2 = new Piece("Scout",2,10,is_user);
        Piece SCOUT_3 = new Piece("Scout",2,10,is_user);
        Piece SCOUT_4 = new Piece("Scout",2,10,is_user);
        Piece SCOUT_5 = new Piece("Scout",2,10,is_user);
        Piece SCOUT_6 = new Piece("Scout",2,10,is_user);
        Piece SCOUT_7 = new Piece("Scout",2,10,is_user);
        Piece SCOUT_8 = new Piece("Scout",2,10,is_user);

        Piece SPY_1  = new Piece("Spy",1,1,is_user);

        Piece FLAG = new Piece("Flag",0,0,is_user);

        //Default Board Configuration
        Piece[][] board_pieces = this.board_pieces;

        board_pieces[i][0] = SCOUT_1;
        board_pieces[i][1] = MINER_1;
        board_pieces[i][2] = BOMB_1;
        board_pieces[i][3] = FLAG;
        board_pieces[i][4] = BOMB_2;
        board_pieces[i][5] = MINER_2;
        board_pieces[i][6] = MINER_3;
        board_pieces[i][7] = COLONEL_1;
        board_pieces[i][8] = GENERAL_1;
        board_pieces[i][9] = SERGEANT_1;

        i += increment;

        board_pieces[i][0] = SPY_1;
        board_pieces[i][1] = LIEUTENANT_1;
        board_pieces[i][2] = MINER_4;
        board_pieces[i][3] = BOMB_3;
        board_pieces[i][4] = LIEUTENANT_2;
        board_pieces[i][5] = LIEUTENANT_3;
        board_pieces[i][6] = LIEUTENANT_4;
        board_pieces[i][7] = CAPTAIN_1;
        board_pieces[i][8] = CAPTAIN_2;
        board_pieces[i][9] = MINER_5;

        i += increment;

        board_pieces[i][0] = CAPTAIN_3;
        board_pieces[i][1] = CAPTAIN_4;
        board_pieces[i][2] = MAJOR_1;
        board_pieces[i][3] = MARSHAL_1;
        board_pieces[i][4] = MAJOR_2;
        board_pieces[i][5] = SERGEANT_2;
        board_pieces[i][6] = COLONEL_2;
        board_pieces[i][7] = SERGEANT_3;
        board_pieces[i][8] = SCOUT_2;
        board_pieces[i][9] = BOMB_4;

        i += increment;

        board_pieces[i][0] = SERGEANT_4;
        board_pieces[i][1] = MAJOR_3;
        board_pieces[i][2] = BOMB_5;
        board_pieces[i][3] = SCOUT_3;
        board_pieces[i][4] = BOMB_6;
        board_pieces[i][5] = SCOUT_4;
        board_pieces[i][6] = SCOUT_5;
        board_pieces[i][7] = SCOUT_6;
        board_pieces[i][8] = SCOUT_7;
        board_pieces[i][9] = SCOUT_8;

    }

    public void testBoard(){

        Piece[][] board_pieces = this.board_pieces;
        Piece TEST_1 = new Piece("TEST",6,1,false);
        Piece TEST_2 = new Piece("TEST",6,1,false);
        Piece TEST_3 = new Piece("TEST",6,1,false);
        Piece TEST_4 = new Piece("TEST",6,1,false);
        Piece TEST_5 = new Piece("TEST",6,1,false);
        Piece TEST_6 = new Piece("TEST",6,1,false);
        Piece TEST_7 = new Piece("TEST",6,1,false);
        Piece TEST_8 = new Piece("TEST",6,1,false);


        board_pieces[4][4] = TEST_1;
        board_pieces[4][5] = TEST_2;
        board_pieces[4][8] = TEST_3;
        board_pieces[4][9] = TEST_4;

        board_pieces[5][4] = TEST_5;
        board_pieces[5][5] = TEST_6;
        board_pieces[5][8] = TEST_7;
        board_pieces[5][9] = TEST_8;


    }
}
