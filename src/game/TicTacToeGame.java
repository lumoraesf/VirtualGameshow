package game;

import model.User;

public class TicTacToeGame extends Game {
    private String[][] board;
    private User playerX;
    private User playerO;
    private String currentTurn;
    private String result;

    public TicTacToeGame(User playerX, User playerO) {
        super("Tic-Tac-Toe");
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentTurn = "X";
        this.result = "";
        this.board = new String[3][3];
        clearBoard();
    }

    public TicTacToeGame(User playerX, User playerO, String state) {
        this(playerX, playerO);
        loadState(state);
    }

    @Override
    public void startGame() {
        setFinished(false);
    }

    @Override
    public void saveGame() {
    }

    public boolean makeMove(int row, int col) {
        if (isFinished() || row < 0 || row > 2 || col < 0 || col > 2 || board[row][col].length() > 0) {
            return false;
        }
        board[row][col] = currentTurn;
        String winner = checkWinner();
        if (winner.length() > 0) {
            result = winner;
            setFinished(true);
            setScore(10);
        } else if (isDraw()) {
            result = "DRAW";
            setFinished(true);
            setScore(5);
        } else {
            currentTurn = currentTurn.equals("X") ? "O" : "X";
        }
        return true;
    }

    public String checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (same(board[i][0], board[i][1], board[i][2])) {
                return board[i][0];
            }
            if (same(board[0][i], board[1][i], board[2][i])) {
                return board[0][i];
            }
        }
        if (same(board[0][0], board[1][1], board[2][2])) {
            return board[0][0];
        }
        if (same(board[0][2], board[1][1], board[2][0])) {
            return board[0][2];
        }
        return "";
    }

    public boolean isDraw() {
        if (checkWinner().length() > 0) {
            return false;
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].length() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getCell(int row, int col) {
        return board[row][col];
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public User getCurrentPlayer() {
        return currentTurn.equals("X") ? playerX : playerO;
    }

    public User getPlayerX() {
        return playerX;
    }

    public User getPlayerO() {
        return playerO;
    }

    public String getResult() {
        return result;
    }

    public void restart() {
        clearBoard();
        currentTurn = "X";
        result = "";
        setScore(0);
        setFinished(false);
    }

    public String serializeState() {
        StringBuilder cells = new StringBuilder();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                String value = board[row][col];
                cells.append(value.length() == 0 ? "-" : value);
            }
        }
        return cells.toString() + ";" + currentTurn;
    }

    private void loadState(String state) {
        if (state == null || state.trim().length() == 0) {
            return;
        }
        String[] parts = state.split(";", -1);
        if (parts.length > 0 && parts[0].length() == 9) {
            int index = 0;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    char value = parts[0].charAt(index++);
                    board[row][col] = value == '-' ? "" : String.valueOf(value);
                }
            }
        }
        if (parts.length > 1 && (parts[1].equals("X") || parts[1].equals("O"))) {
            currentTurn = parts[1];
        }
    }

    private boolean same(String a, String b, String c) {
        return a.length() > 0 && a.equals(b) && b.equals(c);
    }

    private void clearBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = "";
            }
        }
    }
}
