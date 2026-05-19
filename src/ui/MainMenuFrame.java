package ui;

import game.PasapalabraGame;
import manager.SaveGameManager;
import manager.StatisticsManager;
import manager.UserManager;
import model.SavedGame;
import model.User;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class MainMenuFrame extends JFrame {
    private UserManager userManager;
    private User currentUser;

    public MainMenuFrame(UserManager userManager, User currentUser) {
        this.userManager = userManager;
        this.currentUser = currentUser;
        setTitle("Main Menu");
        setSize(420, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildLayout();
    }

    private void buildLayout() {
        JPanel buttons = new JPanel(new GridLayout(currentUser.isAdmin() ? 7 : 6, 1, 8, 8));
        JButton pasapalabraButton = new JButton("Play Pasapalabra");
        JButton ticTacToeButton = new JButton("Play Tic-Tac-Toe");
        JButton continueButton = new JButton("Continue Saved Game");
        JButton statisticsButton = new JButton("View My Statistics");
        JButton logoutButton = new JButton("Logout");

        buttons.add(pasapalabraButton);
        buttons.add(ticTacToeButton);
        buttons.add(continueButton);
        buttons.add(statisticsButton);
        if (currentUser.isAdmin()) {
            JButton adminButton = new JButton("Admin Panel");
            buttons.add(adminButton);
            adminButton.addActionListener(e -> new AdminFrame(userManager).setVisible(true));
        }
        buttons.add(logoutButton);

        add(new JLabel("Welcome, " + currentUser.getUsername(), JLabel.CENTER), BorderLayout.NORTH);
        add(buttons, BorderLayout.CENTER);

        pasapalabraButton.addActionListener(e -> new PasapalabraFrame(currentUser, new PasapalabraGame(), null).setVisible(true));
        ticTacToeButton.addActionListener(e -> startTicTacToe());
        continueButton.addActionListener(e -> continueSavedGame());
        statisticsButton.addActionListener(e -> new StatisticsFrame(currentUser.getUsername(), new StatisticsManager().getResultsForUser(currentUser.getUsername())).setVisible(true));
        logoutButton.addActionListener(e -> {
            new LoginFrame(userManager).setVisible(true);
            dispose();
        });
    }

    private void startTicTacToe() {
        String playerTwoUsername = JOptionPane.showInputDialog(this, "Player 2 username");
        if (playerTwoUsername == null) {
            return;
        }
        String playerTwoPassword = JOptionPane.showInputDialog(this, "Player 2 password");
        User playerTwo = userManager.login(playerTwoUsername, playerTwoPassword == null ? "" : playerTwoPassword);
        if (playerTwo == null || playerTwo.getUsername().equals(currentUser.getUsername())) {
            JOptionPane.showMessageDialog(this, "Player 2 must be another registered user.");
            return;
        }
        new TicTacToeFrame(currentUser, playerTwo, null, null).setVisible(true);
    }

    private void continueSavedGame() {
        SaveGameManager manager = new SaveGameManager();
        List<SavedGame> savedGames = manager.getSavedGamesForUser(currentUser.getUsername());
        if (savedGames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No saved games found.");
            return;
        }
        DefaultListModel<SavedGame> model = new DefaultListModel<SavedGame>();
        for (SavedGame savedGame : savedGames) {
            model.addElement(savedGame);
        }
        JList<SavedGame> list = new JList<SavedGame>(model);
        int option = JOptionPane.showConfirmDialog(this, new JScrollPane(list), "Choose Saved Game", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION || list.getSelectedValue() == null) {
            return;
        }
        SavedGame savedGame = list.getSelectedValue();
        if (savedGame.getGameType().equals("Pasapalabra")) {
            new PasapalabraFrame(currentUser, new PasapalabraGame(savedGame.getGameState()), savedGame.getId()).setVisible(true);
        } else if (savedGame.getGameType().equals("Tic-Tac-Toe")) {
            User playerOne = userManager.login(savedGame.getPlayerOne(), findPassword(savedGame.getPlayerOne()));
            User playerTwo = userManager.login(savedGame.getPlayerTwo(), findPassword(savedGame.getPlayerTwo()));
            if (playerOne != null && playerTwo != null) {
                new TicTacToeFrame(playerOne, playerTwo, savedGame.getGameState(), savedGame.getId()).setVisible(true);
            }
        }
    }

    private String findPassword(String username) {
        for (User user : userManager.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user.getPassword();
            }
        }
        return "";
    }
}
