package com.example.quizapp;

public class Question {
    private String questionText;
    private String[] options;
    private int correctAnswerIndex;
    private String category;

    public Question(String questionText, String[] options, int correctAnswerIndex, String category) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.category = category;
    }

    // Getters
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
    public String getCategory() { return category; }

    // Setters
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setOptions(String[] options) { this.options = options; }
    public void setCorrectAnswerIndex(int correctAnswerIndex) { this.correctAnswerIndex = correctAnswerIndex; }
    public void setCategory(String category) { this.category = category; }
}