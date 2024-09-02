// FE --> "Front End"
package csc133;

import java.util.Scanner;

public class gameFE {

    mechanicsBE.slTTTBoard my_board;

    gameFE(mechanicsBE.slTTTBoard game_board) {
        my_board = game_board;
    }

    public void startGame() {
        if (promptToStart()) {
            my_board.playRandom();
        }
        my_board.printBoard();
        int game_status = mechanicsBE.slTTTBoard.GAME_INCOMPLETE;
        while (my_board.GAME_QUIT != game_status) {
            print_exit_message(game_status);
            my_board.resetBoard();
            game_status = my_board.play();
        }

        print_exit_message(game_status);
        // Game has ended; display a message here based on game_status

    }  // public static void startGame()

    //  return true --> machine; false --> player
    private static boolean promptToStart() {
        boolean retVal = false;
        System.out.println("\nWould you like to start? 'n' for machine to start");
        System.out.print("'y' if you want to start the game:\n");
        Scanner my_scanner = new Scanner(System.in);
        if (my_scanner.hasNext()) {
            char inChar = my_scanner.next().charAt(0);
            if (inChar == 'n' || inChar == 'y') {
                retVal = (inChar == 'n');
            }  //  if (inChar == 'n' || inChar == 'y')
        }
        return retVal;
    }  //  boolean promptToStart()

    private static void print_exit_message ( int exit_status){
        switch (exit_status) {
            case mechanicsBE.slTTTBoard.GAME_QUIT:
                System.out.println("Sorry to see you go; come again!");
                break;
            case mechanicsBE.slTTTBoard.GAME_PLAYER:
                System.out.println("Congratulations! you have won!");
                break;
            case mechanicsBE.slTTTBoard.GAME_MACHINE:
                System.out.println("Sorry, you did not win; try again!");
                break;
            case mechanicsBE.slTTTBoard.GAME_DRAW:
                System.out.println("Hey, you almost beat me, let's try again!");
                break;
            default:
                break;
        }
    }  //  private static void print_exit_message(int exit_status)

}
