package csc133;

import mechanicsBE.slTTTBoard;

import static java.lang.System.exit;

public class csc133Driver {
    public static void main(String[] args) {
        slTTTBoard my_board = new slTTTBoard();

        my_board.printBoard();
        while (my_board.GAME_QUIT != my_board.play()) {
            my_board.resetBoard();
        }

        int game_status = slTTTBoard.GAME_INCOMPLETE;
        while (game_status != my_board.GAME_QUIT) {
            game_status = my_board.play();
            if (game_status == my_board.GAME_QUIT) {
                break;
            } else if (askToContinue()) {
                continue;
            } else {
                    break;
            }  // switch (game_status)
        }  // while (end_game)
    }

    private static boolean askToContinue() {
        boolean retVal = false;
        System.out.printf("Would you like to play again? (y/n): ");
        java.util.Scanner myScanner = new java.util.Scanner(System.in);
        while (true) {
            if (myScanner.nextLine().toLowerCase().equals("y")) {
                retVal = true;
                break;
            } else if (myScanner.nextLine().toLowerCase().equals("n")) {
                break;
            } else {
                System.out.println("Invalid input; please type \"y\" or \"n\"");
            }
        }  // while (true)
        return retVal;
    }  // boolean askToContinue()


}  // public class csc133Driver


