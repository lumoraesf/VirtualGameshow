package ui;

import manager.StatisticsManager;
import manager.UserManager;
import model.GameResult;
import model.User;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.util.List;

public class AdminFrame extends JFrame {
    private UserManager userManager;
    private StatisticsManager statisticsManager;

    public AdminFrame(UserManager userManager) {
        this.userManager = userManager;
        this.statisticsManager = new StatisticsManager();
        setTitle("Admin Panel");
        setSize(720, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildLayout();
    }

    private void buildLayout() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Users", usersTable());
        tabs.add("Pasapalabra Ranking", resultsTable(statisticsManager.getRankingForGame("Pasapalabra")));
        tabs.add("Tic-Tac-Toe Ranking", resultsTable(statisticsManager.getRankingForGame("Tic-Tac-Toe")));
        tabs.add("Recent Games", resultsTable(statisticsManager.getRecentGames()));
        add(tabs, BorderLayout.CENTER);
    }

    private JScrollPane usersTable() {
        List<User> users = userManager.getAllUsers();
        String[] columns = {"Username", "Admin"};
        String[][] rows = new String[users.size()][2];
        for (int i = 0; i < users.size(); i++) {
            rows[i][0] = users.get(i).getUsername();
            rows[i][1] = String.valueOf(users.get(i).isAdmin());
        }
        return new JScrollPane(new JTable(rows, columns));
    }

    private JScrollPane resultsTable(List<GameResult> results) {
        String[] columns = {"Username", "Game", "Date", "Score", "Result"};
        String[][] rows = new String[results.size()][5];
        for (int i = 0; i < results.size(); i++) {
            GameResult result = results.get(i);
            rows[i][0] = result.getUsername();
            rows[i][1] = result.getGameName();
            rows[i][2] = result.getDate();
            rows[i][3] = String.valueOf(result.getScore());
            rows[i][4] = result.getResult();
        }
        return new JScrollPane(new JTable(rows, columns));
    }
}
