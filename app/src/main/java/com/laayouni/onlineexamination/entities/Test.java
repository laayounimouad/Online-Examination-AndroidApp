package com.laayouni.onlineexamination.entities;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {
    private Long id;
    private String name;
    private String link;
    private String code;
    private User owner;
    private List<Question> questions;
    private List<User> users;

    public Test(String name){
        this.name=name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Test(String name, String link, String code, User owner) {
        this.name = name;
        this.link = link;
        this.code = code;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return  name;
    }
}
