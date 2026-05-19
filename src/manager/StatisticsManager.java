package manager;

import model.GameResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class StatisticsManager {
    public void saveResult(String username, String gameName, int score, String result) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        FileManager.appendLine(FileManager.STATISTICS_FILE, new GameResult(username, gameName, date, score, result).toFileLine());
    }

    public List<GameResult> getResultsForUser(String username) {
        List<GameResult> results = new ArrayList<GameResult>();
        for (GameResult result : getAllResults()) {
            if (result.getUsername().equals(username)) {
                results.add(result);
            }
        }
        return results;
    }

    public List<GameResult> getRankingForGame(String gameName) {
        List<GameResult> results = new ArrayList<GameResult>();
        for (GameResult result : getAllResults()) {
            if (result.getGameName().equals(gameName)) {
                results.add(result);
            }
        }
        Collections.sort(results, new Comparator<GameResult>() {
            @Override
            public int compare(GameResult a, GameResult b) {
                return b.getScore() - a.getScore();
            }
        });
        return results;
    }

    public List<GameResult> getRecentGames() {
        List<GameResult> results = getAllResults();
        Collections.reverse(results);
        if (results.size() > 20) {
            return new ArrayList<GameResult>(results.subList(0, 20));
        }
        return results;
    }

    public List<GameResult> getAllResults() {
        List<GameResult> results = new ArrayList<GameResult>();
        List<String> lines = FileManager.readFile(FileManager.STATISTICS_FILE);
        for (String line : lines) {
            GameResult result = GameResult.fromFileLine(line);
            if (result != null) {
                results.add(result);
            }
        }
        return results;
    }
}
