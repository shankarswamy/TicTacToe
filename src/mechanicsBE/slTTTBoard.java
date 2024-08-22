package mechanicsBE;

import java.util.Scanner;

public class slTTTBoard {
    private final int ROW = 3, COL = 3;
    private char[][] ttt_board = new char[ROW][COL];
    private int[] row_status = {0, 0, 0};
    private int[] col_status = {0, 0, 0};
    private int[] diag_status = {0, 0};  // {LeadDiagonal, TrailingDiagonal}
    // track the min/max status values for each of ldiag, tdiag, row, col:
    int[] winning_status = new int[] {0, 0, 0, 0};
    int[] losing_status = new int[] {0, 0, 0, 0};

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









    private boolean computerPlay() {
        boolean continuePlay = true;  // return value;
        final int RR=0, CC = 1, LD=2, TD=4;
        // row, col, LDiag, TDiag:

        losing_status[LD]  = findRepeatsLDiagonal(player_char);
        losing_status[TD]  = findRepeatsTDiagonal(player_char);
        losing_status[RR]  = Math.min(Math.min(row_status[0], row_status[1]), row_status[2]);
        losing_status[CC]  = Math.min(Math.min(col_status[0], col_status[1]), col_status[2]);

        winning_status[LD] = findRepeatsLDiagonal(machine_char);
        winning_status[TD] = findRepeatsTDiagonal(machine_char);
        winning_status[RR]  = Math.max(Math.max(row_status[0], row_status[1]), row_status[2]);
        winning_status[CC]  = Math.min(Math.max(col_status[0], col_status[1]), col_status[2]);


        if (winning_status[RR] == 3 || winning_status[CC] == 3 ||
                                        winning_status[LD] == 3 || winning_status[TD] == 3) {
            winner_char = machine_char;
            System.out.println("Game over! Winner is " + winner_char);
            continuePlay = false;
        } else if (losing_status[RR] == -3 || losing_status[CC] == -3 ||
                                        losing_status[LD] == -3 || losing_status[TD] == -3) {
            winner_char = player_char;
            System.out.println("Game over! Winner is " + winner_char);
            continuePlay = false;
        }

        if (continuePlay && winning_status[RR] == 2) {
            for (int i = 0; i < ttt_board.length; i++) {
                if (findRepeatsInRow(i, machine_char) == 2) {

                    winner_char = machine_char;
                    continuePlay = false;
                }
            }
        }







        return continuePlay;
    }  //  private boolean computerPlay()

    private boolean isGameOver() {
        boolean retVal = false;
        // check rows:
        for (int row = 0; row < ttt_board.length; row++) {
            if (ttt_board[row][0] == default_char) {continue;}
            if (ttt_board[row][0] == ttt_board[row][1] && ttt_board[row][1] == ttt_board[row][2]) {
                winner_char = ttt_board[row][2];
                retVal = true;
                break;
            }  // if (my_board[row][0] == ...)
        }  // for (int row = 0; ...)

        if (!retVal) {
            // check along cols:
            for (int col = 0; col < ttt_board.length; col++) {
                if (ttt_board[0][col] == default_char) {continue;}
                if (ttt_board[0][col] == ttt_board[1][col] && ttt_board[1][col] == ttt_board[2][col]) {
                    winner_char = ttt_board[2][col];
                    retVal = true;
                    break;
                }  // if (my_board[col][0] == ...)
            }  // for (int col = 0; ...)
        }  // if (!retVal)

        // diagonals:
        if (!retVal) {
            // leading diagonal:
            if ( (ttt_board[0][0] != default_char) &&
                    (ttt_board[0][0] == ttt_board[1][1] && ttt_board[1][1] == ttt_board[2][2]) ) {
                retVal = true;
            } else {
                // trailing diagonal
                if ( (ttt_board[0][2] != default_char) &&
                        (ttt_board[0][2] == ttt_board[1][1] && ttt_board[1][1] == ttt_board[2][0]) ) {
                    retVal = true;
                }
            }
        }  //  if (!retVal)

        return retVal;
    }  // private boolean isGameOver()

    public int[] getInput() {
        Scanner my_scanner = new Scanner(System.in);
        //System.out.println("Enter row  col - with space and no comma or any other character:\n");
        int row = my_scanner.nextInt();
        int col = my_scanner.nextInt();
        
        System.out.printf("you entered: (%d, %d) \n", row, col);

        return new int[] {row, col};
    }

    // returns true if the game ended on wrong input:
    public void play() {
        boolean retVal = true;
        int row = -1, col = -1;
        boolean game_over = false;
        while (!game_over) {
            System.out.printf("Enter the row and col for your entry - space (only) separated:    ");
            int [] my_input = getInput();
            if (my_input.length != 2) {
                retVal = false;
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

            game_over = isGameOver();
        }
        return;
    }  // public void play()



    // All scores: from the perspective of computer winning.  0 --> no entries in the row
    // 1 --> two away from winning with this row, 2 --> one away from winning,
    // 3 --> won!  -1 --> 2 away from losing this row, -2 --> one away from losing,
    // -3 lost the row.


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
