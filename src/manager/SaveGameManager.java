package manager;

import model.SavedGame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveGameManager {
    public SavedGame saveGame(String gameType, String playerOne, String playerTwo, String gameState) {
        String id = String.valueOf(System.currentTimeMillis());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        SavedGame savedGame = new SavedGame(id, gameType, playerOne, playerTwo, gameState, date);
        FileManager.appendLine(FileManager.SAVED_GAMES_FILE, savedGame.toFileLine());
        return savedGame;
    }

    public List<SavedGame> getSavedGamesForUser(String username) {
        List<SavedGame> savedGames = new ArrayList<SavedGame>();
        for (SavedGame savedGame : getAllSavedGames()) {
            if (savedGame.belongsTo(username)) {
                savedGames.add(savedGame);
            }
        }
        return savedGames;
    }

    public List<SavedGame> getAllSavedGames() {
        List<SavedGame> savedGames = new ArrayList<SavedGame>();
        for (String line : FileManager.readFile(FileManager.SAVED_GAMES_FILE)) {
            SavedGame savedGame = SavedGame.fromFileLine(line);
            if (savedGame != null) {
                savedGames.add(savedGame);
            }
        }
        return savedGames;
    }

    public void removeSavedGame(String id) {
        List<String> lines = new ArrayList<String>();
        for (SavedGame savedGame : getAllSavedGames()) {
            if (!savedGame.getId().equals(id)) {
                lines.add(savedGame.toFileLine());
            }
        }
        FileManager.writeFile(FileManager.SAVED_GAMES_FILE, lines);
    }
}
