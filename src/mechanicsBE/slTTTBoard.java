package mechanicsBE;

import java.util.Scanner;

public class slTTTBoard {
    private final int ROW = 3, COL = 3;
    private char[][] ttt_board = new char[ROW][COL];
    private int[] row_status = {0, 0, 0};
    private int[] col_status = {0, 0, 0};
    private int[] diag_status = {0, 0};  // {LeadDiagonal, TrailingDiagonal}

    private final char default_char = '-';
    private final char machine_char = 'X';
    private final char player_char = 'P';
    private char winner_char = default_char;

     public slTTTBoard() {
        for (int row = 0; row < ttt_board.length; row++) {
            for (int col = 0; col < ttt_board[row].length; col++) {
                ttt_board[row][col] = default_char;
            }  //  for (int col = 0; col < my_board[row].length; col++)
        }  //  for (int row = 0; row < my_board.length; row++)
    }  // void slTTTBoard()

    // Update the scores row, col, and diags for the given (row, col)
    private boolean updateTTTBoard(int row, int col) {
        boolean retVal = false;
        if (ttt_board[row][col] != default_char) {
            return retVal;
        }
        updateRowStatus(row);
        updateColStatus(col);
        if (row == col) {
            updateLDiagonalStatus();
        }
        if (row + col == 2) {
            updateTDiagonalStatus();
        }

        return true;
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

    private boolean playLDiagonal() {
        boolean retVal = false;
        for (int i =0; i < ttt_board.length; i++) {
            if (ttt_board[i][i] == default_char) {
                ttt_board[i][i] = machine_char;
                retVal = true;
                break;
            }
        }
        return retVal;
    }  //  boolean playLDiagonal(int diag)

    private boolean playTDiagonal() {
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
    }  //  boolean playTDiagonal()

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

    private boolean playLDiag() {
         boolean retVal = false;
         if ( (findRepeatsLDiagonal(machine_char) == 2) ||
                 (findRepeatsLDiagonal(player_char) == 2)) {
             for (int i = 0; i < ttt_board.length; i++) {
                 if (ttt_board[i][i] == default_char) {
                     ttt_board[i][i] = machine_char;
                     retVal = true;
                 }
             }
         }
         return retVal;
    }  //  private boolean playLDiag()

    private boolean playTDiag() {
         boolean retVal = false;
         if ( (findRepeatsTDiagonal(machine_char) == 2) ||
                 (findRepeatsTDiagonal(player_char) == 2) ) {
             for (int i = 0; i < COL; i++) {
                 if (ttt_board[i][COL - 1 - i] == default_char) {
                     ttt_board[i][COL - 1 - i] = machine_char;
                     retVal = true;
                 }
             }
        }
         return retVal;
    }  // private boolean playTDiag()

    private boolean playRow(int row_num) {
         boolean retVal = false;
         for (int col = 0; col < ttt_board[row_num].length; ++col) {
             if (ttt_board[row_num][col] == default_char) {
                 ttt_board[row_num][col] = machine_char;
                 winner_char = machine_char;
                 retVal = true;
                 break;
             }
         }
         return retVal;
    }  //  private boolean playRow(int row_num)

    private boolean playCol(int col_num) {
         boolean retVal = false;
         for (int row = 0; row < ttt_board.length; ++row) {
             if (ttt_board[row][col_num] == default_char) {
                 ttt_board[row][col_num] = machine_char;
                 winner_char = machine_char;
                 retVal = true;
                 break;
             }
         }
         return retVal;
    }  // private boolean playCol(int col_num)

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

    // returns true if the game ended on wrong input:
    public void play() {
        boolean retVal = true;
        int row = -1, col = -1;
        boolean game_over = false;
        while (!game_over) {
            retVal = true;
            System.out.printf("Enter the row and col for your entry - space (only) separated:    ");
            int [] my_input = promptReadInput();
            if (my_input.length != 2) {
                retVal = false;
            }
            if (my_input[0] == -1) {
                retVal = false;
                return;
            }
            if (retVal) {
                row = my_input[0];
                col = my_input[1];
                if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                    System.out.println("Invalid row or col. Try again.");
                    retVal = false;
                }
                if (ttt_board[row][col] != default_char) {
                    System.out.println("That cell is already marked. Try again.");
                    retVal = false;
                }
            }  //  if (retVal)

            if (retVal) {
                ttt_board[row][col] = player_char;
                updateTTTBoard(row, col);
                printBoard();
            }

            //game_over = isGameOver();
        }
        return;
    }  // public void play()

    // Call this after each update of my_board:
    private void updateRowStatus(int my_row) {

        if (row_status[my_row] == 4) {return;}

        int[] score;
        // update row score
        score = new int[] {findRepeatsInRow(my_row, player_char),
                                findRepeatsInCol(my_row, machine_char)};
        if (score[0] > 0 && score[1] > 0) { row_status[my_row] = 4; return;}
        if (score[0] == 3) {row_status[my_row] = -3; return;}
        if (score[1] == 3) {row_status[my_row] = 3; return; }
        if ((score[0] + score[1])  == 2) {
            if (score[0] == 0) {row_status[my_row] = 2; return;}
            else {row_status[my_row] = -2; return;}
        }
        else {
            // default = 2, either player or machine == 1:
            if (score[0] == 1) {row_status[my_row] = -1; return;}
            else {row_status[my_row] = 1; return;}
        }
    }  //  private void updateRowStatus(int[] row_col)

    private void updateColStatus(int my_col) {

         if (col_status[my_col] == 4) {return;}

        int[] score;
        // update row score
        score = new int[] {findRepeatsInCol(my_col, player_char),
                               findRepeatsInCol(my_col, machine_char)};
        if (score[0] > 0 && score[1] > 0) { col_status[my_col] = 4; return;}
        if (score[0] == 3) {col_status[my_col] = -3; return;}
        if (score[1] == 3) {col_status[my_col] = 3; return; }
        if ((score[0] + score[1])  == 2) {
            if (score[0] == 0) {col_status[my_col] = 2; return;}
            else {col_status[my_col] = -2; return;}
        }
        else {
            // default = 2, either player or machine == 1:
            if (score[0] == 1) {col_status[my_col] = -1; return;}
            else {col_status[my_col] = 1; return;}
        }
    }  //  private void updateColStatus(int[] row_col)

    private void updateLDiagonalStatus() {

        final int LD = 0; // "Leading Diagonal"
        if (diag_status[LD] == 4) {return;}

        int[] score;
        score = new int[] {findRepeatsLDiagonal(player_char), findRepeatsLDiagonal(machine_char)};
        if (score[0] > 0 && score[1] > 0) { diag_status[LD] = 4; return;}
        if (score[0] == 3) {diag_status[LD] = -3; return;}
        if (score[1] == 3) {diag_status[LD] = 3; return; }
        if ((score[0] + score[1])  == 2) {
            if (score[0] == 0) {diag_status[LD] = 2; return;}
            else {diag_status[LD] = -2; return;}
        }
        else {
            // default = 2, either player or machine == 1:
            if (score[0] == 1) {diag_status[LD] = -1; return;}
            else {diag_status[LD] = 1; return;}
        }
    }  //  private void updateLDiagonalStatus()

    private void updateTDiagonalStatus() {
        final int TD = 1; // "Leading Diagonal"
        if (diag_status[TD] == 4) {return;}

        int[] score;
        score = new int[] {findRepeatsLDiagonal(player_char), findRepeatsLDiagonal(machine_char)};
        if (score[0] > 0 && score[1] > 0) { diag_status[TD] = 4; return;}
        if (score[0] == 3) {diag_status[TD] = -3; return;}
        if (score[1] == 3) {diag_status[TD] = 3; return; }
        if ((score[0] + score[1])  == 2) {
            if (score[0] == 0) {diag_status[TD] = 2; return;}
            else {diag_status[TD] = -2; return;}
        }
        else {
            // default = 2, either player or machine == 1:
            if (score[0] == 1) {diag_status[TD] = -1; return;}
            else {diag_status[TD] = 1; return;}
        }
    }  //  private void updateTDiagonalStatus()

    // Return the [row, col] the computer entered for its move


    public void printBoard() {
        for (char[] my_chars : ttt_board) {
            System.out.printf("%c   %c   %c\n\n", my_chars[0], my_chars[1], my_chars[2]);
        }  //  for (int row = 0; row < my_board.length; ++row)
    }  //  public void printBoard()

}  //  public class slTTTBoard
