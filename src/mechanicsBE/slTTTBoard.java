package mechanicsBE;

import java.util.Scanner;

public class slTTTBoard {
    private final int ROW = 3, COL = 3;
    private char[][] my_board = new char[ROW][COL];
    private int[] row_status = {0, 0, 0};
    private int[] col_status = {0, 0, 0};
    private int[] diag_status = {0, 0};  // first element: lead diagonal

    private final char default_char = '-';
    private final char machine_char = 'X';
    private final char player_char = 'O';
    private char winner_char = default_char;

     public slTTTBoard() {
        for (int row = 0; row < my_board.length; row++) {
            for (int col = 0; col < my_board[row].length; col++) {
                my_board[row][col] = default_char;
            }  //  for (int col = 0; col < my_board[row].length; col++)
        }  //  for (int row = 0; row < my_board.length; row++)
    }  // void slTTTBoard()

    private boolean updateBoard(int row, int col, char my_c) {
        boolean retVal = true;
        if (my_c != machine_char  && my_c != player_char) {
            retVal = false;
        }

        if (retVal && my_board[row][col] != default_char) {
            retVal = false;
        }

        if (retVal) {
            my_board[row][col] = my_c;
        }

        return retVal;
    }  // private boolean updateBoard(int row, int col, char my_c)

    private boolean isGameOver() {
        boolean retVal = false;
        // check rows:
        for (int row = 0; row < my_board.length; row++) {
            if (my_board[row][0] == default_char) {continue;}
            if (my_board[row][0] == my_board[row][1] && my_board[row][1] == my_board[row][2]) {
                winner_char = my_board[row][2];
                retVal = true;
                break;
            }  // if (my_board[row][0] == ...)
        }  // for (int row = 0; ...)

        if (!retVal) {
            // check along cols:
            for (int col = 0; col < my_board.length; col++) {
                if (my_board[0][col] == default_char) {continue;}
                if (my_board[0][col] == my_board[1][col] && my_board[1][col] == my_board[2][col]) {
                    winner_char = my_board[2][col];
                    retVal = true;
                    break;
                }  // if (my_board[col][0] == ...)
            }  // for (int col = 0; ...)
        }  // if (!retVal)

        // diagonals:
        if (!retVal) {
            // leading diagonal:
            if ( (my_board[0][0] != default_char) &&
                    (my_board[0][0] == my_board[1][1] && my_board[1][1] == my_board[2][2]) ) {
                retVal = true;
            } else {
                // trailing diagonal
                if ( (my_board[0][2] != default_char) &&
                        (my_board[0][2] == my_board[1][1] && my_board[1][1] == my_board[2][0]) ) {
                    retVal = true;
                }
            }
        }  //  if (!retVal)

        return retVal;
    }  // private boolean isGameOver()

    public int[] getInput() {
        Scanner my_scanner = new Scanner(System.in);
        System.out.println("Enter row  col - with space and no comma or any other character:\n");
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
            System.out.println("Enter the row and col for your entry - space (only) separated:");
            int [] my_input = getInput();
            if (my_input.length != 2) {
                retVal = false;
            }
            if (retVal) {
                row = my_input[0];
                col = my_input[1];
                if (row < 0 || row >= 3 || col < 0 || col >= 3 ||
                                my_board[row][col] != default_char) {
                    System.out.println("Invalid row or col. Try again.");
                    retVal = false;
                }
            }  //  if (retVal)

            if (retVal) {
                my_board[row][col] = player_char;
                printBoard();
            }
            game_over = isGameOver();
        }
        return;
    }  // public void play()

    private int findRepeatsInRow(int row, int s_type) {
         int num_repeats = 0;
         for (int col = 0; col < my_board[row].length; col++) {
             if (my_board[row][col] == s_type) {
                 ++num_repeats;
             }
         }
         return num_repeats;
    }  //  int findRepeatsInRow

    private int findRepeatsInCol(int col, int s_type) {
         int num_repeats = 0;
         for (int row = 0; row < my_board.length; row++) {
             if (my_board[row][col] == s_type) {
                 ++num_repeats;
             }
         }  //  for (int row = 0; ...)

         return num_repeats;
    }  //  int findRepeatsInCol

    private int findRepeatsLDiagonal(int s_type) {
         int num_repeats = 0;
         for (int row = 0; row < my_board.length; row++) {
             if (my_board[row][row] == s_type) {
                 ++num_repeats;
             }
         }
         return num_repeats;
    }  //  private int findRepeatsLDiagonal(int s_type)

    private int findRepeatsTDiagonal(int s_type) {
         int num_repeats = 0;
         if (my_board[ROW-3][ROW-1] == s_type) { ++num_repeats;}
         if (my_board[ROW-2][ROW-2] == s_type) { ++num_repeats;}
         if (my_board[ROW-1][ROW-3] == s_type) { ++num_repeats;}
         return num_repeats;
    }  //  private int findRepeatsTDiagonal(int s_type)

    // All scores: from the perspective of computer winning.  0 --> no entries in the row
    // 1 --> two away from winning with this row, 2 --> one away from winning,
    // 3 --> won!  -1 --> 2 away from losing this row, -2 --> one away from losing,
    // -3 lost the row.
    private int[] scoreARow(int row) {
         return new int[] {findRepeatsInRow(row, player_char), findRepeatsInCol(row, machine_char)};
    }  // private int scoreARow(int row)

    private int[] scoreAColumn(int col) {
         return new int[] {findRepeatsInCol(col, player_char),  findRepeatsInCol(col, machine_char)};

    }  //  private int scoreAColumn(int co)

    private int[] scoreLDiagonal() {
         return new int[] {findRepeatsLDiagonal(player_char), findRepeatsLDiagonal(machine_char)};
    }

    private int[] scoreTDiagonal() {
         return new int[] {findRepeatsTDiagonal(player_char), findRepeatsTDiagonal(machine_char)};
    }

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
        for (char[] my_chars : my_board) {
            System.out.printf("%c   %c   %c\n\n", my_chars[0], my_chars[1], my_chars[2]);
        }  //  for (int row = 0; row < my_board.length; ++row)
    }  //  public void printBoard()

}  //  public class slTTTBoard
