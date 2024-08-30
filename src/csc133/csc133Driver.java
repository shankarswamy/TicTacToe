package csc133;

import com.sun.source.tree.BreakTree;
import mechanicsBE.slTTTBoard;

import static java.lang.System.exit;

public class csc133Driver {
    public static void main(String[] args) {
        slTTTBoard my_board = new slTTTBoard();

        my_board.printBoard();
        int game_status = slTTTBoard.GAME_INCOMPLETE;
        while (my_board.GAME_QUIT != game_status) {
            print_exit_message(game_status);
            my_board.resetBoard();
            game_status = my_board.play();
        }

        print_exit_message(game_status);
    }  // public static void main(String[] args)

    // Game has ended; display a message here based on game_status
    private static void print_exit_message(int exit_status){
        switch (exit_status){
            case slTTTBoard.GAME_QUIT:
                System.out.println("Sorry to see you go; come again!");
                break;
            case slTTTBoard.GAME_PLAYER:
                System.out.println("Congratulations! you have won!");
                break;
            case slTTTBoard.GAME_MACHINE:
                System.out.println("Sorry, you did not win; try again!");
                break;
            case slTTTBoard.GAME_DRAW:
                System.out.println("Hey, you almost beat me, let's try again!");
                break;
            default:
                break;
        }
    }  //  private static void print_exit_message(int exit_status)

}  // public class csc133Driver


