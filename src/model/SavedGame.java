package model;

public class SavedGame {
    private String id;
    private String gameType;
    private String playerOne;
    private String playerTwo;
    private String gameState;
    private String dateSaved;

    public SavedGame(String id, String gameType, String playerOne, String playerTwo, String gameState, String dateSaved) {
        this.id = id;
        this.gameType = gameType;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameState = gameState;
        this.dateSaved = dateSaved;
    }

    public String getId() {
        return id;
    }

    public String getGameType() {
        return gameType;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public String getGameState() {
        return gameState;
    }

    public String getDateSaved() {
        return dateSaved;
    }

    public boolean belongsTo(String username) {
        return playerOne.equals(username) || playerTwo.equals(username);
    }

    public String toFileLine() {
        return id + "|" + gameType + "|" + playerOne + "|" + playerTwo + "|" + gameState + "|" + dateSaved;
    }

    public static SavedGame fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 6) {
            return null;
        }
        return new SavedGame(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }

    @Override
    public String toString() {
        String second = playerTwo == null || playerTwo.length() == 0 ? "" : " vs " + playerTwo;
        return id + " - " + gameType + " - " + playerOne + second + " - " + dateSaved;
    }
}
