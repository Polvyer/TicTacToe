import java.io.Serializable;

public class GameInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    int player = 0;                  // Holds Player #
    String difficulty = "";          // Holds "Easy", "Medium", or "Expert"
    int wins = 0;                    // Holds # of wins
    int buttonClickedClient = 0;     // Buttons 1-9 (0 not used)
    int buttonClickedServer = 0;     // Buttons 1-9 (0 not used)
    String[] board = new String[10]; // Holds current state of the game in array format
    String input = "";               // Holds current state of the game in string format
    Integer[] top3 = new Integer[4]; // top[1] = #1, top[2] = #2, top[3] = #3 (0 not used)
    Integer[] top3Players = new Integer[4]; // top[1] = #1, top[2] = #2, top[3] = #3 (0 not used)
    int winCondition = -1; // Win = 0 (tie), Win = 1 (client wins), Win = 2 (server wins)

    // Constructor (initializes board and top3)
    GameInfo() {
        for (int i = 1; i <= 9; i++) {
            board[i] = "b";
        }
        for (int i = 1; i <= 3; i++) {
            top3[i] = 0;
        }
        for (int i = 1; i <= 3; i++) {
            top3Players[i] = 0;
        }
    }

    // Constructs current state in string format
    void updateInput() {
        for (int i = 1; i <= 9; i++) {
            if (i == 9) {
                input += board[i];
            }
            else {
                input += board[i] + " ";
            }
        }
    }

    // Updates board with client "O" pick
    void updateBoardClient() {
        board[buttonClickedClient] = "O";
    }

    // Updates board with server "X" pick
    void updateBoardServer() {
        board[buttonClickedServer] = "X";
    }

    // Makes input empty
    void resetInput() {
        input = "";
    }

    // Checks if the board is full (if board full, return true, else false)
    boolean boardFull() {
        int count = 0;
        for (int i = 1; i <= 9; i++) {
            if (board[i].equals("b")) {
                count++;
            }
        }

        if (count == 0) {
            return true;
        }

        return false;
    }

    // Check if either the client or server has won (if won, return true, else false)
    // Send in "X" for server or "O" for client
    boolean checkWin(String piece) {
        if (board[1].equals(piece) && board[2].equals(piece) && board[3].equals(piece)) {
            return true;
        }
        else if (board[4].equals(piece) && board[5].equals(piece) && board[6].equals(piece)) {
            return true;
        }
        else if (board[7].equals(piece) && board[8].equals(piece) && board[9].equals(piece)) {
            return true;
        }
        else if (board[1].equals(piece) && board[4].equals(piece) && board[7].equals(piece)) {
            return true;
        }
        else if (board[2].equals(piece) && board[5].equals(piece) && board[8].equals(piece)) {
            return true;
        }
        else if (board[3].equals(piece) && board[6].equals(piece) && board[9].equals(piece)) {
            return true;
        }
        else if (board[1].equals(piece) && board[5].equals(piece) && board[9].equals(piece)) {
            return true;
        }
        else if (board[3].equals(piece) && board[5].equals(piece) && board[7].equals(piece)) {
            return true;
        }
        else {
            return false;
        }
    }

    void reset() {
        difficulty = "";             // Holds "Easy", "Medium", or "Expert"
        buttonClickedClient = 0;     // Buttons 1-9 (0 not used)
        buttonClickedServer = 0;     // Buttons 1-9 (0 not used)
        for (int i = 1; i <= 9; i++) {
            board[i] = "b";
        }
        input = "";            // Holds current state of the game in string format
        winCondition = -1; // Win = 0 (tie), Win = 1 (client wins), Win = 2 (server wins)
    }

}