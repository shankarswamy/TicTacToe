package mechanicsBE;

import java.util.Scanner;

public class slTTTBoard {
    private final int ROW = 3, COL = 3;
    private char[][] ttt_board = new char[ROW][COL];

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
                printBoard();
            }
        }
        return;
    }  // public void play()

    public void printBoard() {
        for (char[] my_chars : ttt_board) {
            System.out.printf("%c   %c   %c\n\n", my_chars[0], my_chars[1], my_chars[2]);
        }  //  for (int row = 0; row < my_board.length; ++row)
    }  //  public void printBoard()

}  //  public class slTTTBoard
