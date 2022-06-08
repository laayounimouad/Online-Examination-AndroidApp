package com.laayouni.onlineexamination.entities;

import java.io.Serializable;

public class Choice implements Serializable {
    private Long id;
    private String choice;
    private boolean isAnswer;
    private Question question;

    public Choice(String choice, boolean isAnswer) {
        this.choice = choice;
        this.isAnswer = isAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
