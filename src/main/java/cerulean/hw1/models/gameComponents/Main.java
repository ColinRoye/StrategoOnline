package cerulean.hw1.models.gameComponents;

import cerulean.hw1.models.Game;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        Scanner scanner = new Scanner(System.in);
        //Create new account
        //Account myAccount = new Account("test","12345");

        //Create New game
        Game game = new Game("123");
        Board gameBoard = game.getBoard();
        gameBoard.setDefaultBoard(true,9);
        gameBoard.setDefaultBoard(false,0);

        gameBoard.printToConsole();


        String menuOption = ("MENU OPTIONS\n" +
                "M : MAKE PLAYER MOVE\n" +
                "P : PRINT OUT BOARD\n" +
                "Q : QUIT\n" +
                "INPUT : " );

        System.out.print(menuOption);
        String input = scanner.nextLine();

        while(!input.equalsIgnoreCase("Q")){

            switch(input){
                case "M":
                case "m":
                    System.out.println("Enter piece to move and location to move to\n" +
                            "FORMAT row_1,col_1:row_2,col_2 : ");
                    input = scanner.nextLine();
                    String[] splitString = input.split(":");
                    int[] moveFrom = Arrays.stream(splitString[0].split(",")).mapToInt(Integer::parseInt).toArray();
                    int[] moveTo = Arrays.stream(splitString[1].split(",")).mapToInt(Integer::parseInt).toArray();

                    Move moveResult = null;
                    try {
                        moveResult = game.move(moveFrom,moveTo,true);
                        int[] ai_coords = game.runAI();
                        Move aiMove = game.move(new int[]{ai_coords[0], ai_coords[1]}, new int[]{ai_coords[2], ai_coords[3]},false);
                        System.out.printf("\nAI MOVED FROM %d,%d TO %d,%d \n ",ai_coords[0],ai_coords[1],ai_coords[2],ai_coords[3]);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if(moveResult == null)
                        System.out.println("INVALID MOVE");
                    else{
                        System.out.println("MOVE SUCCESSFUL");
                        gameBoard.printToConsole();
                    }


                    break;
                case "p":
                case "P":
                    gameBoard.printToConsole();
                    break;
                default:
                    System.out.println("Unknown input");
            }



            System.out.println("--------------------------------------\n");
            System.out.print(menuOption);
            input = scanner.nextLine();
        }
        System.out.println("--EXIT--");


        //At this point default board has been set

        //Now to setup the board




    }


}