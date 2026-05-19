package game;

public abstract class Game implements Saveable {
    private String gameName;
    private int score;
    private boolean finished;

    public Game(String gameName) {
        this.gameName = gameName;
        this.score = 0;
        this.finished = false;
    }

    public abstract void startGame();

    public void pauseGame() {
    }

    public String getGameName() {
        return gameName;
    }

    public int getScore() {
        return score;
    }

    protected void setScore(int score) {
        this.score = score;
    }

    public boolean isFinished() {
        return finished;
    }

    protected void setFinished(boolean finished) {
        this.finished = finished;
    }
}
