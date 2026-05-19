package model;

public class GameResult {
    private String username;
    private String gameName;
    private String date;
    private int score;
    private String result;

    public GameResult(String username, String gameName, String date, int score, String result) {
        this.username = username;
        this.gameName = gameName;
        this.date = date;
        this.score = score;
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public String getGameName() {
        return gameName;
    }

    public String getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    public String getResult() {
        return result;
    }

    public String toFileLine() {
        return username + "|" + gameName + "|" + date + "|" + score + "|" + result;
    }

    public static GameResult fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 5) {
            return null;
        }
        try {
            return new GameResult(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4]);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
