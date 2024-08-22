package mechanicsBE;

import java.util.Random;
import java.util.Scanner;

public class slTTTBoard {
    public static final int GAME_DRAW = 0, GAME_PLAYER = 1,
                    GAME_MACHINE = 2, GAME_INCOMPLETE = 3, GAME_QUIT = 4;

    private final int ROW = 3, COL = 3;
    private char[][] ttt_board = new char[ROW][COL];

    private final char default_char = '-';
    private final char machine_char = 'X';
    private final char player_char = 'O';
    private char winner_char = default_char;

    private void init() {
        for (int row = 0; row < ttt_board.length; row++) {
            for (int col = 0; col < ttt_board[row].length; col++) {
                ttt_board[row][col] = default_char;
            }  //  for (int col = 0; col < my_board[row].length; col++)
        }  //  for (int row = 0; row < my_board.length; row++)
        winner_char = default_char;
    }  //  private void init()
     public slTTTBoard() {
        init();
    }  // void slTTTBoard()

    public void resetBoard() {
        init();
    }  //  public void resetBoard()

    public int[] promptReadInput() {
        boolean retVal = false;
        Scanner my_scanner = new Scanner(System.in);
        int row = -1, col = -1;
        while (!retVal) {
            System.out.print("Enter row col numbers (space separated):    ");
            if (my_scanner.hasNextInt()) {
                row = my_scanner.nextInt();
                col = my_scanner.nextInt();
                if (row < ROW && col < COL) {
                    retVal = true;
                }
            } else {
                String my_string = my_scanner.nextLine();
                if (my_string.contains("q") || my_string.contains("Q")) {
                    System.out.println("Good bye - come again!");
                    return new int[]{-1, -1};
                } else {
                    System.out.println("Invalid input! Try again.");
                }
            }
        }  // while(!retVal)
        return new int[] {row, col};
    }  //  promptReadInput()

    private boolean playLDiag() {
        boolean retVal = false;
        for (int i =0; i < ttt_board.length; i++) {
            if (ttt_board[i][i] == default_char) {
                ttt_board[i][i] = machine_char;
                retVal = true;
                break;
            }
        }
        return retVal;
    }  //  boolean playLDiag(int diag)

    private boolean playTDiag() {
        boolean retVal = false;
        int MAX_COL = 3;
        for (int i =0; i < ttt_board.length; i++) {
            if (ttt_board[i][MAX_COL-1-i] == default_char) {
                ttt_board[i][MAX_COL-1-i] = machine_char;
                retVal = true;
                break;
            }
        }
        return retVal;
    }  //  boolean playTDiag()

    private boolean playTheRow(int row) {
        boolean retVal = false;
        for (int col = 0; col < ttt_board[row].length; col++) {
            if (ttt_board[row][col] == default_char) {
                ttt_board[row][col] = machine_char;
                retVal = true;
                break;
            }
        }
        return retVal;
    }  //  boolean playRow(int row)

    private boolean playTheCol(int col) {
        boolean retVal = false;
        for (int row = 0; row < ttt_board.length; row++) {
            if (ttt_board[row][col] == default_char) {
                ttt_board[row][col] = machine_char;
                retVal = true;
                break;
            }
        }
        return retVal;
    }  //  boolean playTheCol(int col)

    // my_char == machine_char: play to win; my_char == player_char: play to avert losing
    private boolean playToConclude(char my_char) {
        boolean retVal = false;
        if (my_char != player_char && my_char != machine_char) { return retVal;}
        // try LD, TD, rows and columns in that order
        if (findRepeatsLDiagonal(my_char) == 2) {
            retVal = playLDiag();
        }
        if (!retVal && findRepeatsTDiagonal(my_char) == 2) {
            retVal = playTDiag();
        }
        if (!retVal) {
            for (int row = 0; row < ttt_board.length; ++row) {
                if (findRepeatsInRow(row, my_char) != 2) { continue; }
                retVal = playTheRow(row);
                if (retVal) { break; }
            }  //  for (int row = 0; row < ttt_board.length; ++row)
        }
        if (!retVal) {
           for (int col = 0; col < ttt_board.length; ++col) {
               if (findRepeatsInCol(col, my_char) != 2) { continue; }
               retVal = playTheCol(col);
               if (retVal) { break; }
           }  //  for (int col = 0; col < ttt_board.length; ++col)
        }

        if ((my_char == machine_char) && retVal ) { winner_char = my_char;}

        return retVal;
    }  //  private boolean playToConclude()

    private boolean playRandomPick() {
        boolean retVal = false;
        if (ttt_board[1][1] == default_char) {ttt_board[1][1] = machine_char; retVal = true;}
        if (!retVal) { retVal = playLDiag(); }
        if (!retVal) { retVal = playTDiag(); }
        if (!retVal) {
            // pick remaining rows in some random order:
            Random my_rand = new Random();
            int min = 1, max = 2;
            int randInt = my_rand.nextInt(max) + min;
            if (randInt == 1) {
                retVal = playTheRow(2);
                if (!retVal) { playTheCol(2);}
            } else {
                retVal = playTheCol(2);
                if (!retVal) { playTheRow(2);}
            }
        }  // if (!retVal)
        return retVal;
    }  //  private boolean playRandomPick()

    // Update the scores row, col, and diags for the given (row, col)
    private boolean updateTTTBoard(int row, int col) {
        boolean retVal = false;
        if (ttt_board[row][col] != default_char) {
            return retVal;
        }
        ttt_board[row][col] = player_char;
        retVal = playToConclude(machine_char);
        if (!retVal) { retVal = playToConclude(player_char); }
        if (!retVal) { retVal = playRandomPick(); }


        return retVal;
    }  // private boolean updateTTTBoard(int row, int col)

    private int findRepeatsLDiagonal(int s_type) {
        int num_repeats = 0;
        for (int row = 0; row < ttt_board.length; row++) {
            if (ttt_board[row][row] == s_type) {
                ++num_repeats;
            }
        }
        return num_repeats;
    }  //  private int findRepeatsLDiagonal(int s_type)

    private int findRepeatsTDiagonal(int s_type) {
        int num_repeats = 0;
        if (ttt_board[ROW-3][ROW-1] == s_type) { ++num_repeats;}
        if (ttt_board[ROW-2][ROW-2] == s_type) { ++num_repeats;}
        if (ttt_board[ROW-1][ROW-3] == s_type) { ++num_repeats;}
        return num_repeats;
    }  //  private int findRepeatsTDiagonal(int s_type)

    private int findRepeatsInRow(int row, int s_type) {
        int num_repeats = 0;
        for (int col = 0; col < ttt_board[row].length; col++) {
            if (ttt_board[row][col] == s_type) {
                ++num_repeats;
            }
        }
        return num_repeats;
    }  //  int findRepeatsInRow

    private int findRepeatsInCol(int col, int s_type) {
        int num_repeats = 0;
        for (int row = 0; row < ttt_board.length; row++) {
            if (ttt_board[row][col] == s_type) {
                ++num_repeats;
            }
        }  //  for (int row = 0; ...)

        return num_repeats;
    }  //  int findRepeatsInCol

    private int isGameOver() {
        int retVal = GAME_INCOMPLETE;
        if ( (findRepeatsLDiagonal(player_char) == 3) ||
             (findRepeatsTDiagonal(player_char) == 3) ||
             (findRepeatsInRow(0, player_char) == 3) ||
             (findRepeatsInRow(1, player_char) == 3) ||
             (findRepeatsInRow(2, player_char) == 3) ||
             (findRepeatsInCol(0, player_char) == 3) ||
             (findRepeatsInCol(1, player_char) == 3) ||
             (findRepeatsInCol(2, player_char) == 3) ) {
            retVal = GAME_PLAYER; }
        else if ( (findRepeatsLDiagonal(machine_char) == 3) ||
             (findRepeatsTDiagonal(machine_char) == 3) ||
             (findRepeatsInRow(0, machine_char) == 3) ||
             (findRepeatsInRow(1, machine_char) == 3) ||
             (findRepeatsInRow(2, machine_char) == 3) ||
             (findRepeatsInCol(0, machine_char) == 3) ||
             (findRepeatsInCol(1, machine_char) == 3) ||
             (findRepeatsInCol(2, machine_char) == 3) ) {
            retVal = GAME_MACHINE;
        } else if ( (findRepeatsInRow(0, player_char) +
                findRepeatsInRow(0, machine_char) == 3) &&
                (findRepeatsInRow(1, player_char)  +
                        findRepeatsInRow(1, machine_char) == 3) &&
                (findRepeatsInRow(2, player_char)  +
                        findRepeatsInRow(2, machine_char) == 3) ) {
            retVal = GAME_DRAW;
        }
        return retVal;
    }  // private int isGameOver()

    // returns true if the game ended on wrong input:
    public int play() {
        int retVal = GAME_INCOMPLETE;
        int row = -1, col = -1;
        int game_over = GAME_INCOMPLETE;
        System.out.println("Enter the row and col for your entry - space (only) separated; type 'q' to quit");
        while (game_over == GAME_INCOMPLETE) {
            int [] my_input = promptReadInput();
            if (my_input[0] < 0) {
                // user ended the game or something went wrong: exit
                retVal = GAME_QUIT;
            }
            if (retVal == GAME_INCOMPLETE) {
                row = my_input[0];
                col = my_input[1];
                if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                    System.out.println("Invalid row or col. Try again.");
                    continue;
                }
                if (ttt_board[row][col] != default_char) {
                    System.out.println("That cell is already marked. Try again.");
                    continue;
                }
                updateTTTBoard(row, col);
                printBoard();

                game_over = isGameOver();
                switch (game_over) {
                    case GAME_PLAYER:
                        System.out.println("Congratulations! You won!");
                        return game_over;
                    case GAME_MACHINE:
                        System.out.println("You did not win this time; try again!");
                        return game_over;
                    case GAME_DRAW:
                        System.out.println("Wow! Not bad - it was a draw!");
                        return game_over;
                    default:
                        break;
                }  //  switch (game_over)
            }  //  if (retVal == GAME_INCOMPLETE)

        }  //  while (game_over == GAME_INCOMPLETE)

        if (winner_char == player_char) {
            System.out.println("Game over. You have won! Give me a chance to win: play again!");
            init();
            play();
        }

        return game_over;
    }  // public int play()

    public void printBoard() {
        for (char[] my_chars : ttt_board) {
            System.out.printf("%c   %c   %c\n\n", my_chars[0], my_chars[1], my_chars[2]);
        }  //  for (int row = 0; row < my_board.length; ++row)
    }  //  public void printBoard()

}  //  public class slTTTBoard
