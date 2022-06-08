package com.laayouni.onlineexamination.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    private Long id;
    private String question;
    private List<Choice> choices=new ArrayList<>();
    private Test test;

    public Question(String question) {
        this.question = question;
    }

    public Question(String question, List<Choice> choices) {
        this.question = question;
        this.choices = choices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Choice> getChoices() {
        return choices;
    }
    public Choice getCorrectAnswer(){
        return choices.stream().filter(choice -> choice.isAnswer()==true).findFirst().get();
    }
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
