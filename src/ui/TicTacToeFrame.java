package ui;

import game.TicTacToeGame;
import manager.SaveGameManager;
import manager.StatisticsManager;
import model.User;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class TicTacToeFrame extends JFrame {
    private TicTacToeGame game;
    private String savedGameId;
    private JButton[][] cells;
    private JLabel statusLabel;

    public TicTacToeFrame(User playerX, User playerO, String state, String savedGameId) {
        this.game = state == null ? new TicTacToeGame(playerX, playerO) : new TicTacToeGame(playerX, playerO, state);
        this.savedGameId = savedGameId;
        this.cells = new JButton[3][3];
        setTitle("Tic-Tac-Toe");
        setSize(420, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildLayout();
        refresh();
    }

    private void buildLayout() {
        statusLabel = new JLabel(" ", JLabel.CENTER);
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 4, 4));
        Font cellFont = new Font("Arial", Font.BOLD, 42);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                final int r = row;
                final int c = col;
                cells[row][col] = new JButton("");
                cells[row][col].setFont(cellFont);
                cells[row][col].addActionListener(e -> makeMove(r, c));
                boardPanel.add(cells[row][col]);
            }
        }

        JPanel buttons = new JPanel(new GridLayout(1, 3, 8, 8));
        JButton saveButton = new JButton("Save and Exit");
        JButton restartButton = new JButton("Restart");
        JButton closeButton = new JButton("Close");
        buttons.add(saveButton);
        buttons.add(restartButton);
        buttons.add(closeButton);

        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveAndExit());
        restartButton.addActionListener(e -> {
            game.restart();
            refresh();
        });
        closeButton.addActionListener(e -> dispose());
    }

    private void makeMove(int row, int col) {
        if (!game.makeMove(row, col)) {
            return;
        }
        refresh();
        if (game.isFinished()) {
            finishGame();
        }
    }

    private void finishGame() {
        StatisticsManager statistics = new StatisticsManager();
        if (game.getResult().equals("DRAW")) {
            statistics.saveResult(game.getPlayerX().getUsername(), "Tic-Tac-Toe", 5, "DRAW");
            statistics.saveResult(game.getPlayerO().getUsername(), "Tic-Tac-Toe", 5, "DRAW");
            JOptionPane.showMessageDialog(this, "Draw. Both players receive 5 points.");
        } else {
            User winner = game.getResult().equals("X") ? game.getPlayerX() : game.getPlayerO();
            User loser = game.getResult().equals("X") ? game.getPlayerO() : game.getPlayerX();
            statistics.saveResult(winner.getUsername(), "Tic-Tac-Toe", 10, "WIN");
            statistics.saveResult(loser.getUsername(), "Tic-Tac-Toe", 0, "LOSS");
            JOptionPane.showMessageDialog(this, winner.getUsername() + " wins.");
        }
        if (savedGameId != null) {
            new SaveGameManager().removeSavedGame(savedGameId);
            savedGameId = null;
        }
        refresh();
    }

    private void saveAndExit() {
        if (game.isFinished()) {
            JOptionPane.showMessageDialog(this, "Finished games are saved in statistics.");
            return;
        }
        SaveGameManager manager = new SaveGameManager();
        if (savedGameId != null) {
            manager.removeSavedGame(savedGameId);
        }
        manager.saveGame("Tic-Tac-Toe", game.getPlayerX().getUsername(), game.getPlayerO().getUsername(), game.serializeState());
        JOptionPane.showMessageDialog(this, "Game saved.");
        dispose();
    }

    private void refresh() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cells[row][col].setText(game.getCell(row, col));
                cells[row][col].setEnabled(!game.isFinished() && game.getCell(row, col).length() == 0);
            }
        }
        if (game.isFinished()) {
            statusLabel.setText("Finished: " + game.getResult());
        } else {
            statusLabel.setText("Turn: " + game.getCurrentPlayer().getUsername() + " (" + game.getCurrentTurn() + ")");
        }
    }
}
