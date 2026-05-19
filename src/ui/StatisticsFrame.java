package ui;

import model.GameResult;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.util.List;

public class StatisticsFrame extends JFrame {
    public StatisticsFrame(String title, List<GameResult> results) {
        setTitle("Statistics - " + title);
        setSize(620, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        add(new JScrollPane(new JTable(rows, columns)), BorderLayout.CENTER);
    }
}
