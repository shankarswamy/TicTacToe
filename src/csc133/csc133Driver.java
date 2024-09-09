package csc133;

import mechanicsBE.slTTTBoard;
import static csc133.gameFE.*;

import static csc133.spot.GAME_INCOMPLETE;
import static csc133.spot.GAME_QUIT;


public class csc133Driver {
    private final slTTTBoard my_board = new slTTTBoard();
    private final csc133.gameFE my_fe = new csc133.gameFE();
    public static void main(String[] args) {
        (new csc133Driver()).startGame();

    }  //  public static void main(String] args)

    private void startGame() {
        my_board.printBoard();
        int game_status = GAME_INCOMPLETE;
        while (GAME_QUIT != game_status) {
            my_fe.print_exit_message(game_status);
            my_board.resetBoard();
            if (my_fe.promptToStart()) {
                my_board.playRandom();
                my_board.printBoard();
            }  // if (my_fe.promptToStart())
            game_status = my_board.play();
        }  //  while (...)
        my_fe.promptToStart();
        // Game has ended; display a message here based on game_status
    }  // public static void startGame()

}  // public class csc133Driver


