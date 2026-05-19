package ui;

import game.PasapalabraGame;
import manager.SaveGameManager;
import manager.StatisticsManager;
import model.Question;
import model.User;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PasapalabraFrame extends JFrame {
    private User user;
    private PasapalabraGame game;
    private String savedGameId;
    private JLabel letterLabel;
    private JLabel questionLabel;
    private JLabel scoreLabel;
    private JLabel remainingLabel;
    private JLabel statusLabel;
    private JTextField answerField;

    public PasapalabraFrame(User user, PasapalabraGame game, String savedGameId) {
        this.user = user;
        this.game = game;
        this.savedGameId = savedGameId;
        setTitle("Pasapalabra");
        setSize(620, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildLayout();
        refresh();
    }

    private void buildLayout() {
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        letterLabel = new JLabel();
        scoreLabel = new JLabel();
        remainingLabel = new JLabel();
        statusLabel = new JLabel(" ");
        infoPanel.add(letterLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(remainingLabel);
        infoPanel.add(statusLabel);

        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        questionLabel = new JLabel();
        answerField = new JTextField();
        centerPanel.add(questionLabel, BorderLayout.CENTER);
        centerPanel.add(answerField, BorderLayout.SOUTH);

        JPanel buttons = new JPanel(new GridLayout(1, 4, 8, 8));
        JButton answerButton = new JButton("Answer");
        JButton skipButton = new JButton("Pasapalabra");
        JButton finishButton = new JButton("Finish");
        JButton saveButton = new JButton("Save and Exit");
        buttons.add(answerButton);
        buttons.add(skipButton);
        buttons.add(finishButton);
        buttons.add(saveButton);

        add(infoPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        answerButton.addActionListener(e -> answer());
        skipButton.addActionListener(e -> {
            game.skipQuestion();
            refresh();
        });
        finishButton.addActionListener(e -> finishGame());
        saveButton.addActionListener(e -> saveAndExit());
    }

    private void answer() {
        if (answerField.getText().trim().length() == 0) {
            statusLabel.setText("Type an answer first.");
            return;
        }
        boolean correct = game.checkAnswer(answerField.getText());
        statusLabel.setText(correct ? "Correct." : "Wrong.");
        answerField.setText("");
        if (game.isFinished()) {
            finishGame();
        } else {
            refresh();
        }
    }

    private void finishGame() {
        new StatisticsManager().saveResult(user.getUsername(), "Pasapalabra", game.getScore(), "COMPLETED");
        if (savedGameId != null) {
            new SaveGameManager().removeSavedGame(savedGameId);
        }
        JOptionPane.showMessageDialog(this, "Game finished. Score: " + game.getScore());
        dispose();
    }

    private void saveAndExit() {
        SaveGameManager manager = new SaveGameManager();
        if (savedGameId != null) {
            manager.removeSavedGame(savedGameId);
        }
        manager.saveGame("Pasapalabra", user.getUsername(), "", game.serializeState());
        JOptionPane.showMessageDialog(this, "Game saved.");
        dispose();
    }

    private void refresh() {
        Question question = game.getCurrentQuestion();
        if (question == null) {
            finishGame();
            return;
        }
        letterLabel.setText("Letter: " + question.getLetter());
        questionLabel.setText("<html><h2>" + question.getQuestionText() + "</h2></html>");
        scoreLabel.setText("Score: " + game.getScore());
        remainingLabel.setText("Remaining: " + game.getRemainingQuestions() + " | Correct: " + game.getCorrectAnswers() + " | Wrong: " + game.getWrongAnswers());
    }
}
