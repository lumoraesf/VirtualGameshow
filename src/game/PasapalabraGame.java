package game;

import manager.FileManager;
import model.Question;

import java.util.ArrayList;
import java.util.List;

public class PasapalabraGame extends Game {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int correctAnswers;
    private int wrongAnswers;

    public PasapalabraGame() {
        super("Pasapalabra");
        this.questions = loadQuestions();
        this.currentQuestionIndex = 0;
    }

    public PasapalabraGame(String state) {
        this();
        loadState(state);
    }

    @Override
    public void startGame() {
        setFinished(false);
    }

    @Override
    public void saveGame() {
    }

    public Question getCurrentQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(currentQuestionIndex);
            if (!question.isAnswered()) {
                return question;
            }
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
        }
        setFinished(true);
        return null;
    }

    public boolean checkAnswer(String answer) {
        Question question = getCurrentQuestion();
        if (question == null) {
            setFinished(true);
            return false;
        }
        boolean correct = question.getCorrectAnswer().equalsIgnoreCase(answer.trim());
        question.setAnswered(true);
        if (correct) {
            correctAnswers++;
            setScore(getScore() + 10);
        } else {
            wrongAnswers++;
        }
        moveToNextQuestion();
        updateFinished();
        return correct;
    }

    public void skipQuestion() {
        moveToNextQuestion();
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getRemainingQuestions() {
        int remaining = 0;
        for (Question question : questions) {
            if (!question.isAnswered()) {
                remaining++;
            }
        }
        return remaining;
    }

    public String serializeState() {
        StringBuilder answered = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).isAnswered()) {
                if (answered.length() > 0) {
                    answered.append(",");
                }
                answered.append(i);
            }
        }
        return currentQuestionIndex + ";" + getScore() + ";" + correctAnswers + ";" + wrongAnswers + ";" + answered.toString();
    }

    private void loadState(String state) {
        if (state == null || state.trim().length() == 0) {
            return;
        }
        String[] parts = state.split(";", -1);
        try {
            if (parts.length > 0) {
                currentQuestionIndex = Integer.parseInt(parts[0]);
            }
            if (parts.length > 1) {
                setScore(Integer.parseInt(parts[1]));
            }
            if (parts.length > 2) {
                correctAnswers = Integer.parseInt(parts[2]);
            }
            if (parts.length > 3) {
                wrongAnswers = Integer.parseInt(parts[3]);
            }
            if (parts.length > 4 && parts[4].length() > 0) {
                String[] indexes = parts[4].split(",");
                for (String index : indexes) {
                    int i = Integer.parseInt(index);
                    if (i >= 0 && i < questions.size()) {
                        questions.get(i).setAnswered(true);
                    }
                }
            }
            updateFinished();
        } catch (NumberFormatException ex) {
            currentQuestionIndex = 0;
        }
    }

    private void moveToNextQuestion() {
        if (!questions.isEmpty()) {
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
        }
    }

    private void updateFinished() {
        setFinished(getRemainingQuestions() == 0);
    }

    private List<Question> loadQuestions() {
        List<Question> loadedQuestions = new ArrayList<Question>();
        for (String line : FileManager.readFile(FileManager.QUESTIONS_FILE)) {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 3 && parts[0].length() > 0) {
                loadedQuestions.add(new Question(parts[0].charAt(0), parts[1], parts[2]));
            }
        }
        return loadedQuestions;
    }
}
