// FE --> "Front End"
package csc133;

import java.util.Scanner;

import mechanicsBE.slTTTBoard;
import csc133.spot.*;

import static csc133.spot.*;
import static java.lang.System.exit;

public class gameFE {

    slTTTBoard my_board;

    gameFE(slTTTBoard game_board) {
        my_board = game_board;
    }

    public void startGame() {
        my_board.printBoard();
        int game_status = GAME_INCOMPLETE;
        while (GAME_QUIT != game_status) {
            print_exit_message(game_status);
            my_board.resetBoard();
            if (promptToStart()) {
                my_board.playRandom();
                my_board.printBoard();
            }
            game_status = my_board.play();
        }  //  while (...)
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
            if (inChar == 'q') {
                print_exit_message(GAME_QUIT);
                exit(0);
            }
        }  // if (my_scanner.hasNext())
        return retVal;
    }  //  boolean promptToStart()

    private static void print_exit_message ( int exit_status){
        switch (exit_status) {
            case GAME_QUIT:
                System.out.println("Sorry to see you go; come again!");
                break;
            case GAME_PLAYER:
                System.out.println("Congratulations! you have won!");
                break;
            case GAME_MACHINE:
                System.out.println("Sorry, you did not win; try again!");
                break;
            case GAME_DRAW:
                System.out.println("Hey, you almost beat me, let's try again!");
                break;
            default:
                break;
        }
    }  //  private static void print_exit_message(int exit_status)

}
