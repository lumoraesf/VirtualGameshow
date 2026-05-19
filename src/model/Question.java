package model;

public class Question {
    private char letter;
    private String questionText;
    private String correctAnswer;
    private boolean answered;

    public Question(char letter, String questionText, String correctAnswer) {
        this.letter = letter;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.answered = false;
    }

    public char getLetter() {
        return letter;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
